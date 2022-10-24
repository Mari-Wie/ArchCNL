package org.archcnl.domain.input.visualization.rules;

import java.util.List;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.Visualizer;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

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
    protected List<Triplet> subjectTriplets;
    protected List<Triplet> predicateTriplets;
    protected List<Triplet> objectTriplets;

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
        // TODO Auto-generated method stub
        return null;
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
