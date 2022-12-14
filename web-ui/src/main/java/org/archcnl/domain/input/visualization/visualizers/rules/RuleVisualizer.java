package org.archcnl.domain.input.visualization.visualizers.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.MappingTranslator;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredVariant;
import org.archcnl.domain.input.visualization.diagram.PlantUmlBlock;
import org.archcnl.domain.input.visualization.diagram.PlantUmlPart;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.MappingFlattener;
import org.archcnl.domain.input.visualization.helpers.MappingSetter;
import org.archcnl.domain.input.visualization.visualizers.Visualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.RuleVariant;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhraseContainer;

public abstract class RuleVisualizer implements Visualizer {

    protected ConceptManager conceptManager;
    protected RelationManager relationManager;
    protected String cnlString;
    protected List<Triplet> subjectTriplets;
    protected VerbPhraseContainer verbPhrases;

    protected Set<Variable> usedVariables;
    private List<PlantUmlPart> umlElements;

    protected RuleVisualizer(
            String cnlString,
            List<Triplet> subjectTriplets,
            VerbPhraseContainer verbPhrases,
            ConceptManager conceptManager,
            RelationManager relationManager,
            Set<Variable> usedVariables)
            throws MappingToUmlTranslationFailedException {
        this.cnlString = cnlString;
        this.subjectTriplets = subjectTriplets;
        this.verbPhrases = verbPhrases;
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
        this.usedVariables = usedVariables;
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

    protected void transformToDiagram() throws MappingToUmlTranslationFailedException {
        List<RuleVariant> ruleVariants = buildRuleVariants();
        List<ColoredTriplet> ruleTriplets = new ArrayList<>();
        for (RuleVariant variant : ruleVariants) {
            ruleTriplets.addAll(variant.buildRuleTriplets());
        }
        buildUmlElements(ruleTriplets);
    }

    private void buildUmlElements(List<ColoredTriplet> ruleTriplets)
            throws MappingToUmlTranslationFailedException {
        umlElements = new ArrayList<>();
        new MappingSetter(relationManager, conceptManager).setMappingsInTriplets(ruleTriplets);
        MappingFlattener flattener = new MappingFlattener(ruleTriplets);
        Set<Variable> usedInVisualiztation = new HashSet<>();
        for (ColoredVariant variant : flattener.flattenCustomRelations()) {
            MappingTranslator translator =
                    new MappingTranslator(variant.getTriplets(), conceptManager, relationManager);
            Map<Variable, PlantUmlBlock> elementMap =
                    translator.createElementMap(usedInVisualiztation);
            umlElements.addAll(translator.translateToPlantUmlModel(elementMap));
        }
    }

    protected static List<ColoredTriplet> addPostfixToAllVariables(
            List<Triplet> triplets, String postfix) {
        List<ColoredTriplet> newTriplets = new ArrayList<>();
        for (Triplet triplet : triplets) {
            String oldSubjectName = triplet.getSubject().getName();
            Variable subject = new Variable(oldSubjectName + postfix);
            ObjectType object = triplet.getObject();
            if (object instanceof Variable) {
                String oldObjectName = object.getName();
                object = new Variable(oldObjectName + postfix);
            }
            newTriplets.add(new ColoredTriplet(subject, triplet.getPredicate(), object));
        }
        return newTriplets;
    }

    private List<RuleVariant> buildRuleVariants() throws MappingToUmlTranslationFailedException {
        return verbPhrases.usesAndConnector() ? buildRuleVariantsAnd() : buildRuleVariantsOr();
    }

    protected abstract List<RuleVariant> buildRuleVariantsOr()
            throws MappingToUmlTranslationFailedException;

    protected abstract List<RuleVariant> buildRuleVariantsAnd()
            throws MappingToUmlTranslationFailedException;
}
