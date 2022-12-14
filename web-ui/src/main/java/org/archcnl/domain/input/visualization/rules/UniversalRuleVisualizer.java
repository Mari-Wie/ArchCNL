package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class UniversalRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Every " + "(a |an )?" + SUBJECT_REGEX + " can-only " + PHRASES_REGEX + "\\.");

    public UniversalRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsAnd()
            throws MappingToUmlTranslationFailedException {
        RuleVariant correct = new RuleVariant();
        correct.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "C"));
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            correct.addObjectTriplets(addPostfixToAllVariables(phrase.getObjectTriplets(), "C"));
            correct.addCopyOfPredicate(phrase.getPredicate());
        }

        RuleVariant wrong = new RuleVariant();
        wrong.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "W"));
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            var triplet = phrase.getObjectTriplets().get(0);
            var coloredTriplet =
                    RuleHelper.getTripletWithBaseType(triplet, conceptManager, usedVariables);
            wrong.addObjectTriplets(addPostfixToAllVariables(Arrays.asList(coloredTriplet), "W"));
            wrong.addCopyOfPredicate(phrase.getPredicate());
        }

        correct.setPredicateToColorState(ColorState.CORRECT);
        wrong.setPredicateToColorState(ColorState.WRONG);
        return Arrays.asList(correct, wrong);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsOr()
            throws MappingToUmlTranslationFailedException {
        StringBuilder correctPostfix = new StringBuilder("C");
        StringBuilder wrongPostfix = new StringBuilder("W");
        List<RuleVariant> variants = new ArrayList<>();

        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            RuleVariant correct = new RuleVariant();
            correct.setSubjectTriplets(
                    addPostfixToAllVariables(subjectTriplets, correctPostfix.toString()));
            correct.addObjectTriplets(
                    addPostfixToAllVariables(
                            phrase.getObjectTriplets(), correctPostfix.toString()));
            correct.addCopyOfPredicate(phrase.getPredicate());

            RuleVariant wrong = new RuleVariant();
            wrong.setSubjectTriplets(
                    addPostfixToAllVariables(subjectTriplets, wrongPostfix.toString()));
            var triplet = phrase.getObjectTriplets().get(0);
            var coloredTriplet =
                    RuleHelper.getTripletWithBaseType(triplet, conceptManager, usedVariables);
            wrong.addObjectTriplets(
                    addPostfixToAllVariables(
                            Arrays.asList(coloredTriplet), wrongPostfix.toString()));
            wrong.addCopyOfPredicate(phrase.getPredicate());

            correct.setPredicateToColorState(ColorState.CORRECT);
            wrong.setPredicateToColorState(ColorState.WRONG);

            variants.add(correct);
            variants.add(wrong);
            correctPostfix.append("C");
            wrongPostfix.append("W");
        }
        return variants;
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }

    @Override
    protected Pattern getCnlPattern() {
        return CNL_PATTERN;
    }
}
