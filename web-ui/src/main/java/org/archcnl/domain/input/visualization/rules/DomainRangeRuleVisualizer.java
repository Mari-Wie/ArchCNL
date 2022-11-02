package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.RuleHelper;
import org.archcnl.domain.input.visualization.mapping.ColorState;

public class DomainRangeRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Only " + "(a |an )?" + SUBJECT_REGEX + " can " + PHRASES_REGEX + "\\.");

    public DomainRangeRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
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
        var triplet =
                RuleHelper.getTripletWithBaseType(
                        subjectTriplets.get(0), conceptManager, usedVariables);
        wrong.setSubjectTriplets(addPostfixToAllVariables(Arrays.asList(triplet), "W"));
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            wrong.addObjectTriplets(addPostfixToAllVariables(phrase.getObjectTriplets(), "W"));
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
            var triplet =
                    RuleHelper.getTripletWithBaseType(
                            subjectTriplets.get(0), conceptManager, usedVariables);
            wrong.setSubjectTriplets(
                    addPostfixToAllVariables(Arrays.asList(triplet), wrongPostfix.toString()));
            wrong.addObjectTriplets(
                    addPostfixToAllVariables(phrase.getObjectTriplets(), wrongPostfix.toString()));
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

    @Override
    protected Pattern getCnlPattern() {
        return CNL_PATTERN;
    }
}
