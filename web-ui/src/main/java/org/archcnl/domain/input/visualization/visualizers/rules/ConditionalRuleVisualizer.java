package org.archcnl.domain.input.visualization.visualizers.rules;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class ConditionalRuleVisualizer extends RuleVisualizer {

    private static final String SECOND_PREDICATE_REGEX = "(?<predicate2>[a-z][a-zA-Z]*)";
    private static final String SECOND_OBJECT_REGEX = "(?<object2>[A-Z][a-zA-Z]*( that \\(.+\\))?)";
    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "If "
                            + "(a |an )?"
                            + SUBJECT_REGEX
                            + " "
                            + PHRASES_REGEX
                            + ", then it must "
                            + SECOND_PREDICATE_REGEX
                            + " this (a |an )?"
                            + SECOND_OBJECT_REGEX
                            + "\\.");

    private RulePredicate secondaryPredicate;

    public ConditionalRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        Matcher matcher = CNL_PATTERN.matcher(ruleString);
        RuleHelper.tryToFindMatch(matcher);
        if (!matcher.group("object").equals(matcher.group("object2"))) {
            throw new MappingToUmlTranslationFailedException(cnlString + " Has different objects.");
        }
        String phrasesGroup = matcher.group("phrases");
        verbPhrases = parseVerbPhrases(phrasesGroup);
        subjectTriplets = parseConceptExpression(matcher.group("subject"));
        String secondRelationName = matcher.group("predicate2");
        Relation secondRelation = RuleHelper.getRelation(secondRelationName, relationManager);
        secondaryPredicate = new RulePredicate(secondRelation);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsAnd()
            throws MappingToUmlTranslationFailedException {
        RuleVariant correct = new RuleVariant();
        correct.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "C"));
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            correct.addObjectTriplets(addPostfixToAllVariables(phrase.getObjectTriplets(), "C"));
            correct.addCopyOfPredicate(phrase.getPredicate());
            correct.setCopyOfSecondaryPredicate(secondaryPredicate);
        }

        RuleVariant wrong = new RuleVariant();
        wrong.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "W"));
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            wrong.addObjectTriplets(addPostfixToAllVariables(phrase.getObjectTriplets(), "W"));
            wrong.addCopyOfPredicate(phrase.getPredicate());
        }

        correct.setSubjectToColorState(ColorState.CORRECT);
        wrong.setSubjectToColorState(ColorState.WRONG);
        return Arrays.asList(correct, wrong);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsOr()
            throws MappingToUmlTranslationFailedException {
        throw new UnsupportedOperationException("The conditional rule type doesn't allow OR");
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }

    @Override
    protected Pattern getCnlPattern() {
        return CNL_PATTERN;
    }
}
