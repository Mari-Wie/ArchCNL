package org.archcnl.domain.input.visualization.rules;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

public class UniversalRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Every "
                            + "(a |an )?"
                            + SUBJECT_REGEX
                            + " can-only "
                            + PREDICATE_REGEX
                            + " (a |an )?"
                            + OBJECT_REGEX
                            + "\\.");

    public UniversalRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
    protected List<RuleVariant> buildRuleVariants() throws MappingToUmlTranslationFailedException {
        RuleVariant correct = new RuleVariant();
        correct.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "C"));
        correct.setObjectTriplets(addPostfixToAllVariables(objectTriplets, "C"));
        correct.copyPredicate(predicate);

        RuleVariant wrong = new RuleVariant();
        wrong.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "W"));
        ColoredTriplet triplet = getTripletWithBaseType(objectTriplets.get(0));
        wrong.setObjectTriplets(addPostfixToAllVariables(Arrays.asList(triplet), "W"));
        wrong.copyPredicate(predicate);

        correct.setPredicateToColorState(ColorState.CORRECT);
        wrong.setPredicateToColorState(ColorState.WRONG);
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
