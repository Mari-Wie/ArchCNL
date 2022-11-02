package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
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
import org.archcnl.domain.input.visualization.helpers.RuleHelper;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;
import org.archcnl.domain.input.visualization.mapping.ColoredVariant;
import org.archcnl.domain.input.visualization.rules.VerbPhraseContainer.Connector;

public abstract class RuleVisualizer implements Visualizer {

    protected static final String SUBJECT_REGEX = "(?<subject>[A-Z][a-zA-Z]*( that \\(.+\\))?)";
    protected static final String PREDICATE_REGEX =
            "(?<predicate>[a-z][a-zA-Z]*)( (?<cardinality>(exactly|at-least|at-most)) (?<quantity>\\d+))?";
    protected static final String OBJECT_REGEX =
            "(?<object>(anything|[A-Z][a-zA-Z]*( [A-Z])?( that \\(.+\\))?))";
    private static final String VERB_PHRASE_REGEX = PREDICATE_REGEX + " (a |an )?" + OBJECT_REGEX;
    protected static final String PHRASES_REGEX =
            "(?<phrases>" + VERB_PHRASE_REGEX + "( (and|or) (?<nextPhrase>.*))*)";
    protected static final Pattern conceptExpression =
            Pattern.compile(
                    "(a |an )?(?<concept>[A-Z][a-zA-Z]*)(?<variable> [A-Z])?( that \\((?<that>.*)\\))?");

    protected ConceptManager conceptManager;
    protected RelationManager relationManager;
    protected String cnlString;
    protected List<Triplet> subjectTriplets;
    protected VerbPhraseContainer verbPhrases;

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
        if (SubconceptRuleVisualizer.matches(rule)) {
            return new SubconceptRuleVisualizer(rule, conceptManager, relationManager);
        } else if (ExistentialRuleVisualizer.matches(rule)) {
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

    private void buildUmlElements(List<ColoredTriplet> ruleTriplets)
            throws MappingToUmlTranslationFailedException {
        MappingFlattener flattener = new MappingFlattener(ruleTriplets);
        ColoredVariant flattened = flattener.flattenCustomRelations().get(0);
        MappingTranslator translator =
                new MappingTranslator(flattened.getTriplets(), conceptManager, relationManager);
        Map<Variable, PlantUmlBlock> elementMap = translator.createElementMap(new HashSet<>());
        umlElements = translator.translateToPlantUmlModel(elementMap);
    }

    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        Matcher matcher = getCnlPattern().matcher(ruleString);
        RuleHelper.tryToFindMatch(matcher);
        String phrasesGroup = matcher.group("phrases");
        verbPhrases = parseVerbPhrases(phrasesGroup);
        subjectTriplets = parseConceptExpression(matcher.group("subject"));
    }

    protected List<Triplet> parseConceptExpression(String expression)
            throws MappingToUmlTranslationFailedException {
        Matcher matcher = conceptExpression.matcher(expression);
        RuleHelper.tryToFindMatch(matcher);
        List<Triplet> res = new ArrayList<>();

        String conceptName = matcher.group("concept");
        Concept concept = RuleHelper.getConcept(conceptName, conceptManager);
        Relation typeRelation = TypeRelation.getTyperelation();
        String nextVariableName = matcher.group("variable");

        Variable name;
        if (nextVariableName != null) {
            name = new Variable(nextVariableName);
            res.add(new Triplet(name, typeRelation, concept));
            thatVariables.add(name);
        } else {
            name = NamePicker.pickUniqueVariable(concept.getName(), usedVariables);
            res.add(new Triplet(name, typeRelation, concept));
        }

        if (matcher.group("that") != null) {
            res.addAll(parseThatStatement(matcher.group("that"), name));
        }
        return res;
    }

    protected VerbPhraseContainer parseVerbPhrases(String phrasesGroup)
            throws MappingToUmlTranslationFailedException {
        if (phrasesGroup.contains(" and ") && phrasesGroup.contains(" or ")) {
            throw new MappingToUmlTranslationFailedException(
                    cnlString + " Contains both AND and OR");
        }
        VerbPhraseContainer container = new VerbPhraseContainer();
        if (phrasesGroup.contains(" or ")) {
            container.setConnector(Connector.OR);
        } else {
            container.setConnector(Connector.AND);
        }

        Pattern verbPhrase = Pattern.compile(VERB_PHRASE_REGEX);
        Matcher matcher = verbPhrase.matcher(phrasesGroup);
        while (matcher.find()) {
            RulePredicate predicate = parsePredicate(matcher);
            String objectGroup = matcher.group("object");
            List<Triplet> objectTriplets;
            if ("anything".equals(objectGroup)) {
                var anythingVar = new Variable("anything");
                var triplet =
                        RuleHelper.getBaseObjectTypeTriplet(
                                predicate.getRelation(), anythingVar, usedVariables);
                objectTriplets = Arrays.asList(triplet);
            } else {
                objectTriplets = parseConceptExpression(objectGroup);
            }
            container.addVerbPhrase(new VerbPhrase(predicate, objectTriplets));
        }
        return container;
    }

    private List<Triplet> parseThatStatement(String statement, Variable subject)
            throws MappingToUmlTranslationFailedException {
        VerbPhraseContainer container = parseVerbPhrases(statement);
        List<Triplet> triplets = new ArrayList<>();
        for (VerbPhrase phrase : container.getPhrases()) {
            triplets.addAll(phrase.getObjectTriplets());
            triplets.add(phrase.getTriplet(subject));
        }
        return triplets;
    }

    protected RulePredicate parsePredicate(Matcher matcher)
            throws MappingToUmlTranslationFailedException {
        String name = matcher.group("predicate");
        Relation relation = RuleHelper.getRelation(name, relationManager);
        RulePredicate rulePredicate = new RulePredicate(relation);
        if (matcher.group("cardinality") != null) {
            Cardinality cardinality = Cardinality.getCardinality(matcher.group("cardinality"));
            int quantity = Integer.parseInt(matcher.group("quantity"));
            rulePredicate.setCardinalityLimitations(cardinality, quantity);
        }
        return rulePredicate;
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

    protected abstract Pattern getCnlPattern();
}
