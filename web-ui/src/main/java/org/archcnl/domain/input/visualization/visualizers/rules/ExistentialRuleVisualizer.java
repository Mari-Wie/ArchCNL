package org.archcnl.domain.input.visualization.visualizers.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhrase;

public class ExistentialRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Every " + "(a |an )?" + SUBJECT_REGEX + " must " + PHRASES_REGEX + "\\.");

    public ExistentialRuleVisualizer(
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

        correct.setSubjectToColorState(ColorState.CORRECT);
        wrong.setSubjectToColorState(ColorState.WRONG);
        return Arrays.asList(correct, wrong);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsOr()
            throws MappingToUmlTranslationFailedException {
        StringBuilder correctPostfix = new StringBuilder("C");
        List<RuleVariant> variants = new ArrayList<>();

        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            RuleVariant correct = new RuleVariant();
            correct.setSubjectTriplets(
                    addPostfixToAllVariables(subjectTriplets, correctPostfix.toString()));
            correct.addObjectTriplets(
                    addPostfixToAllVariables(
                            phrase.getObjectTriplets(), correctPostfix.toString()));
            correct.addCopyOfPredicate(phrase.getPredicate());

            correct.setSubjectToColorState(ColorState.CORRECT);

            variants.add(correct);
            correctPostfix.append("C");
        }

        RuleVariant wrong = new RuleVariant();
        wrong.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "W"));
        wrong.setSubjectToColorState(ColorState.WRONG);
        variants.add(wrong);

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
