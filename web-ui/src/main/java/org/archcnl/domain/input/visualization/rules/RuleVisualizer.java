package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
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
            "(?<predicate>[a-z][a-zA-Z]*)( (?<cardinality>(exactly|at-least|at-most)) (?<quantity>\\d+))?";
    protected static final String OBJECT_REGEX =
            "(?<object>(anything|[A-Z][a-zA-Z]*( that \\(.+\\))?))";
    protected static final Pattern conceptExpression =
            Pattern.compile(
                    "(a |an )?(?<concept>[A-Z][a-zA-Z]*)(?<variable> [A-Z])?( that \\((?<relation>[a-z][a-zA-Z]*) (?<that>.*)\\))?");

    protected ConceptManager conceptManager;
    protected RelationManager relationManager;
    protected String cnlString;
    protected List<Triplet> subjectTriplets;
    protected List<Triplet> objectTriplets;
    protected RulePredicate predicate;

    protected Set<Variable> usedVariables = new HashSet<>();
    private Set<Variable> thatVariables = new HashSet<>();
    private List<PlantUmlPart> umlElements;

    protected RuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        this.cnlString = rule.toString();
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
        parseRule(cnlString);
        List<RuleVariant> ruleVariants = buildRuleVariants();
        List<ColoredTriplet> ruleTriplets = new ArrayList<>();
        for (RuleVariant variant : ruleVariants) {
            ruleTriplets.addAll(variant.buildRuleTriplets());
        }
        buildUmlElements(ruleTriplets);
    }

    public static RuleVisualizer createRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        if (containsLogicWords(rule.toString())) {
            throw new MappingToUmlTranslationFailedException(rule + " contains logic words");
        }
        // TODO Add support for Sub-concept rule type and is-a facts
        if (ExistentialRuleVisualizer.matches(rule)) {
            return new ExistentialRuleVisualizer(rule, conceptManager, relationManager);
        } else if (DomainRangeRuleVisualizer.matches(rule)) {
            return new DomainRangeRuleVisualizer(rule, conceptManager, relationManager);
        } else if (UniversalRuleVisualizer.matches(rule)) {
            return new UniversalRuleVisualizer(rule, conceptManager, relationManager);
        } else if (NegationRuleVisualizer.matches(rule)) {
            return new NegationRuleVisualizer(rule, conceptManager, relationManager);
        } else if (ConditionalRuleVisualizer.matches(rule)) {
            return new ConditionalRuleVisualizer(rule, conceptManager, relationManager);
        } else if (CardinalityRuleVisualizer.matches(rule)) {
            return new CardinalityRuleVisualizer(rule, conceptManager, relationManager);
        } else if (FactRuleVisualizer.matches(rule)) {
            return new FactRuleVisualizer(rule, conceptManager, relationManager);
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

    protected List<Triplet> parseConceptExpression(
            String expression,
            Optional<Relation> previousRelation,
            Optional<Variable> previousVariable)
            throws MappingToUmlTranslationFailedException {
        Matcher matcher = conceptExpression.matcher(expression);
        tryToFindMatch(matcher);
        List<Triplet> res = new ArrayList<>();

        Concept concept = getConcept(matcher.group("concept"));
        Relation typeRelation = TypeRelation.getTyperelation();
        String nextVariableName = matcher.group("variable");

        Variable nextVariable;
        if (nextVariableName != null) {
            nextVariable = new Variable(nextVariableName);
            if (!thatVariables.contains(nextVariable)) {
                res.add(new Triplet(nextVariable, typeRelation, concept));
                thatVariables.add(nextVariable);
            }
        } else {
            nextVariable = pickUniqueVariable(concept.getName());
            res.add(new Triplet(nextVariable, typeRelation, concept));
        }

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

    private void buildUmlElements(List<ColoredTriplet> ruleTriplets)
            throws MappingToUmlTranslationFailedException {
        MappingFlattener flattener = new MappingFlattener(ruleTriplets);
        ColoredVariant flattened = flattener.flattenCustomRelations().get(0);
        MappingTranslator translator =
                new MappingTranslator(flattened.getTriplets(), conceptManager, relationManager);
        Map<Variable, PlantUmlBlock> elementMap = translator.createElementMap(new HashSet<>());
        umlElements = translator.translateToPlantUmlModel(elementMap);
    }

    protected Variable pickUniqueVariable(String variableName) {
        String name = NamePicker.getStringWithFirstLetterInLowerCase(variableName);
        Variable nameVariable = new Variable(name);
        return NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), nameVariable);
    }

    protected Relation getRelation(String relationName)
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

    protected ColoredTriplet getBaseSubjectTypeTriplet(Relation relation, Variable subject) {
        subject = NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), subject);
        var objectType = relation.getRelatableSubjectTypes().iterator().next();
        return new ColoredTriplet(subject, TypeRelation.getTyperelation(), objectType);
    }

    protected ColoredTriplet getBaseObjectTypeTriplet(Relation relation, Variable subject) {
        subject = NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), subject);
        var objectType = relation.getRelatableObjectTypes().iterator().next();
        return new ColoredTriplet(subject, TypeRelation.getTyperelation(), objectType);
    }

    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        Matcher matcher = getCnlPattern().matcher(ruleString);
        tryToFindMatch(matcher);
        predicate = parsePredicate(matcher);
        subjectTriplets =
                parseConceptExpression(
                        matcher.group("subject"), Optional.empty(), Optional.empty());
        String objectGroup = matcher.group("object");
        if ("anything".equals(objectGroup)) {
            objectTriplets =
                    Arrays.asList(
                            getBaseObjectTypeTriplet(
                                    predicate.getRelation(), new Variable("anything")));
        } else {
            objectTriplets =
                    parseConceptExpression(objectGroup, Optional.empty(), Optional.empty());
        }
    }

    protected RulePredicate parsePredicate(Matcher matcher)
            throws MappingToUmlTranslationFailedException {
        RulePredicate rulePredicate = new RulePredicate(getRelation(matcher.group("predicate")));
        if (matcher.group("cardinality") != null) {
            Cardinality cardinality = Cardinality.getCardinality(matcher.group("cardinality"));
            int quantity = Integer.parseInt(matcher.group("quantity"));
            rulePredicate.setCardinalityLimitations(cardinality, quantity);
        }
        return rulePredicate;
    }

    protected List<ColoredTriplet> addPostfixToAllVariables(
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

    protected abstract List<RuleVariant> buildRuleVariants()
            throws MappingToUmlTranslationFailedException;

    protected abstract Pattern getCnlPattern();

    private static boolean containsLogicWords(String rule) {
        return rule.contains(" and ") || rule.contains(" or ");
    }
}
