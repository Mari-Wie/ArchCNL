package org.archcnl.domain.input.visualization.visualizers.rules.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;
import org.archcnl.domain.input.visualization.visualizers.rules.RuleHelper;
import org.archcnl.domain.input.visualization.visualizers.rules.RuleVisualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.Cardinality;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.RulePredicate;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhrase;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhraseContainer;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhraseContainer.Connector;

public abstract class RuleParser {

    protected static final String SUBJECT_REGEX = "(?<subject>[A-Z][a-zA-Z]*( that \\(.+\\))?)";
    protected static final String PREDICATE_REGEX =
            "(?<predicate>[a-z][a-zA-Z]*)( (?<cardinality>(exactly|at-least|at-most)) (?<quantity>\\d+))?";
    protected static final String OBJECT_REGEX =
            "(?<object>(anything|[A-Z][a-zA-Z]*( [A-Z])?( that \\([^\\(\\)]+\\))?))";
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

    protected RuleParser(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        this.cnlString = rule.toString();
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
        parseRule(cnlString);
    }

    public static RuleVisualizer createRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        if (SubconceptRuleParser.matches(rule)) {
            return new SubconceptRuleParser(rule, conceptManager, relationManager)
                    .getRuleVisualizer();
        } else if (ExistentialRuleParser.matches(rule)) {
            return new ExistentialRuleParser(rule, conceptManager, relationManager)
                    .getRuleVisualizer();
        } else if (DomainRangeRuleParser.matches(rule)) {
            return new DomainRangeRuleParser(rule, conceptManager, relationManager)
                    .getRuleVisualizer();
        } else if (UniversalRuleParser.matches(rule)) {
            return new UniversalRuleParser(rule, conceptManager, relationManager)
                    .getRuleVisualizer();
        } else if (NegationRuleParser.matches(rule)) {
            return new NegationRuleParser(rule, conceptManager, relationManager)
                    .getRuleVisualizer();
        } else if (ConditionalRuleParser.matches(rule)) {
            return new ConditionalRuleParser(rule, conceptManager, relationManager)
                    .getRuleVisualizer();
        } else if (CardinalityRuleParser.matches(rule)) {
            return new CardinalityRuleParser(rule, conceptManager, relationManager)
                    .getRuleVisualizer();
        } else if (FactRuleParser.matches(rule)) {
            return new FactRuleParser(rule, conceptManager, relationManager).getRuleVisualizer();
        }
        throw new MappingToUmlTranslationFailedException(rule + " couldn't be parsed.");
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
        } else {
            name = NamePicker.pickUniqueVariableName(concept.getName(), usedVariables);
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

    protected abstract RuleVisualizer getRuleVisualizer()
            throws MappingToUmlTranslationFailedException;

    protected abstract Pattern getCnlPattern();
}
