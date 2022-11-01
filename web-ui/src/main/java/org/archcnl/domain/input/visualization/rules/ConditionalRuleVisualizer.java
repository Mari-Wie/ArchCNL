package org.archcnl.domain.input.visualization.rules;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.mapping.ColorState;

public class ConditionalRuleVisualizer extends RuleVisualizer {

    private static final String SECOND_PREDICATE_REGEX = "(?<predicate2>[a-z][a-zA-Z]*)";
    private static final String SECOND_OBJECT_REGEX = "(?<object2>[A-Z][a-zA-Z]*( that \\(.+\\))?)";
    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "If "
                            + "(a |an )?"
                            + SUBJECT_REGEX
                            + " "
                            + PREDICATE_REGEX
                            + " (a |an )?"
                            + OBJECT_REGEX
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
        tryToFindMatch(matcher);
        if (!matcher.group("object").equals(matcher.group("object2"))) {
            throw new MappingToUmlTranslationFailedException(cnlString + " Has different objects.");
        }
        predicate = parsePredicate(matcher);
        secondaryPredicate = new RulePredicate(getRelation(matcher.group("predicate2")));
        subjectTriplets =
                parseConceptExpression(
                        matcher.group("subject"), Optional.empty(), Optional.empty());
        objectTriplets =
                parseConceptExpression(matcher.group("object"), Optional.empty(), Optional.empty());
    }

    @Override
    protected List<RuleVariant> buildRuleVariants() throws MappingToUmlTranslationFailedException {
        RuleVariant correct = new RuleVariant();
        correct.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "C"));
        correct.setObjectTriplets(addPostfixToAllVariables(objectTriplets, "C"));
        correct.copyPredicate(predicate);
        correct.setSecondaryPredicate(secondaryPredicate);

        RuleVariant wrong = new RuleVariant();
        wrong.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "W"));
        wrong.setObjectTriplets(addPostfixToAllVariables(objectTriplets, "W"));
        wrong.copyPredicate(predicate);

        correct.setSubjectToColorState(ColorState.CORRECT);
        wrong.setSubjectToColorState(ColorState.WRONG);
        return Arrays.asList(correct, wrong);
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }

    @Override
    protected Pattern getCnlPattern() {
        return CNL_PATTERN;
    }
}
