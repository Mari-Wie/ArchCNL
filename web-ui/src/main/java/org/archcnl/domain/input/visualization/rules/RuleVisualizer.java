package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.MappingTranslator;
import org.archcnl.domain.input.visualization.PlantUmlBlock;
import org.archcnl.domain.input.visualization.PlantUmlPart;
import org.archcnl.domain.input.visualization.Visualizer;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.MappingFlattener;
import org.archcnl.domain.input.visualization.helpers.NamePicker;
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
    protected String cnlString;
    protected List<Triplet> subjectTriplets;
    protected List<Triplet> objectTriplets;
    protected Relation relation;

    private List<PlantUmlPart> umlElements;
    private Set<Variable> usedVariables = new HashSet<>();

    protected RuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        this.cnlString = rule.toString();
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
        parseRule(cnlString);
        List<ColoredTriplet> ruleTriplets = buildRuleTriplets();
        buildUmlElements(ruleTriplets);
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
        } else if (DomainRangeRuleVisualizer.matches(rule)) {
            return new DomainRangeRuleVisualizer(rule, conceptManager, relationManager);
        } else if (UniversalRuleVisualizer.matches(rule)) {
            return new UniversalRuleVisualizer(rule, conceptManager, relationManager);
        }
        throw new MappingToUmlTranslationFailedException(rule + " couldn't be parsed.");
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

    protected Relation parsePredicate(String group) throws MappingToUmlTranslationFailedException {
        String relationName = group.split(" ")[0];
        // TODO handle cardinality modifiers
        return getRelation(relationName);
    }

    protected List<Triplet> parseConceptExpression(
            String expression,
            Optional<Relation> previousRelation,
            Optional<Variable> previousVariable)
            throws MappingToUmlTranslationFailedException {
        Matcher matcher = conceptExpression.matcher(expression);
        tryToFindMatch(matcher);
        List<Triplet> res = new ArrayList<>();

        Concept concept = getConcept(matcher.group("concept"));
        Variable nextVariable = pickUniqueVariableForConcept(concept);
        Relation typeRelation = TypeRelation.getTyperelation();
        res.add(new Triplet(nextVariable, typeRelation, concept));

        if (previousRelation.isPresent() && previousVariable.isPresent()) {
            res.add(new Triplet(previousVariable.get(), previousRelation.get(), nextVariable));
        }

        if (matcher.group("relation") != null) {
            Relation nextRelation = getRelation(matcher.group("relation"));
            String thatGroup = matcher.group("that");
            res.addAll(
                    parseConceptExpression(
                            thatGroup, Optional.of(nextRelation), Optional.of(nextVariable)));
        }
        return res;
    }

    protected Variable pickUniqueVariableForConcept(Concept concept) {
        String name = NamePicker.getStringWithFirstLetterInLowerCase(concept.getName());
        Variable nameVariable = new Variable(name);
        return NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), nameVariable);
    }

    private Relation getRelation(String relationName)
            throws MappingToUmlTranslationFailedException {
        Optional<Relation> extractedRelation = relationManager.getRelationByName(relationName);
        if (extractedRelation.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(relationName + " doesn't exist");
        }
        return extractedRelation.get();
    }

    protected Concept getConcept(String conceptName) throws MappingToUmlTranslationFailedException {
        Optional<Concept> concept = conceptManager.getConceptByName(conceptName);
        if (concept.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(conceptName + " doesn't exist");
        }
        return concept.get();
    }

    protected void tryToFindMatch(Matcher matcher) throws MappingToUmlTranslationFailedException {
        boolean found = matcher.matches();
        if (!found) {
            throw new MappingToUmlTranslationFailedException("No match found");
        }
    }

    protected ColoredTriplet getTripletWithBaseType(Triplet triplet)
            throws MappingToUmlTranslationFailedException {
        ActualObjectType subjectConcept = getConcept(triplet.getObject().getName());
        if (subjectConcept instanceof CustomConcept) {
            CustomConcept concept = (CustomConcept) subjectConcept;
            Set<ActualObjectType> baseTypes = concept.getBaseTypesFromMapping(conceptManager);
            subjectConcept = baseTypes.iterator().next();
        }
        Relation typeRelation = TypeRelation.getTyperelation();
        String name = NamePicker.getStringWithFirstLetterInLowerCase(subjectConcept.getName());
        Variable nameVariable =
                NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), new Variable(name));
        return new ColoredTriplet(nameVariable, typeRelation, subjectConcept);
    }

    protected abstract void parseRule(String ruleString)
            throws MappingToUmlTranslationFailedException;

    protected abstract List<ColoredTriplet> buildRuleTriplets()
            throws MappingToUmlTranslationFailedException;

    private static boolean containsLogicWords(String rule) {
        return rule.contains(" and ") || rule.contains(" or ");
    }
}
