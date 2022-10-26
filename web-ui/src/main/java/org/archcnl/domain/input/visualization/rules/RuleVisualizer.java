package org.archcnl.domain.input.visualization.rules;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.MappingTranslator;
import org.archcnl.domain.input.visualization.PlantUmlBlock;
import org.archcnl.domain.input.visualization.PlantUmlPart;
import org.archcnl.domain.input.visualization.Visualizer;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.MappingFlattener;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;
import org.archcnl.domain.input.visualization.mapping.ColoredVariant;

public abstract class RuleVisualizer implements Visualizer {

    protected static final String SUBJECT_REGEX = "(?<subject>[A-Z][a-zA-Z]*( that \\(.+\\))?)";
    protected static final String PREDICATE_REGEX =
            "(?<predicate>[a-z][a-zA-Z]*( (exactly|at-least|at-most) \\d+)?)";
    protected static final String OBJECT_REGEX = "(?<object>[A-Z][a-zA-Z]*( that \\(.+\\))?)";
    protected static final Pattern conceptExpression =
            Pattern.compile(
                    "(a |an )?(?<concept>[A-Z][a-zA-Z]*)( that \\((?<relation>[a-z][a-zA-Z]*) (?<that>.*)\\))?");

    protected ConceptManager conceptManager;
    protected RelationManager relationManager;
    private String cnlString;
    private List<PlantUmlPart> umlElements;

    protected RuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager) {
        this.cnlString = rule.toString();
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
    }

    @Override
    public String getName() {
        return cnlString;
    }

    @Override
    public String buildPlantUmlCode() {
        return umlElements.stream()
                .map(PlantUmlPart::buildPlantUmlCode)
                .collect(Collectors.joining("\n"));
    }

    protected void buildUmlElements(List<ColoredTriplet> ruleTriplets)
            throws MappingToUmlTranslationFailedException {
        MappingFlattener flattener = new MappingFlattener(ruleTriplets);
        ColoredVariant flattened = flattener.flattenCustomRelations().get(0);
        MappingTranslator translator =
                new MappingTranslator(flattened.getTriplets(), conceptManager, relationManager);
        Map<Variable, PlantUmlBlock> elementMap = translator.createElementMap(new HashSet<>());
        umlElements = translator.translateToPlantUmlModel(elementMap);
    }

    public static RuleVisualizer createRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        if (containsLogicWords(rule.toString())) {
            throw new MappingToUmlTranslationFailedException(rule + "contains logic words");
        }
        // Important: First check for subconcept
        if (ExistentialRuleVisualizer.matches(rule)) {
            return new ExistentialRuleVisualizer(rule, conceptManager, relationManager);
        }
        throw new MappingToUmlTranslationFailedException(rule + "couldn't be parsed.");
    }

    private static boolean containsLogicWords(String rule) {
        return rule.contains(" and ") || rule.contains(" or ");
    }
}
