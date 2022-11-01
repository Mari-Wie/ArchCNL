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

public class DomainRangeRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Only "
                            + "(a |an )?"
                            + SUBJECT_REGEX
                            + " can "
                            + PREDICATE_REGEX
                            + " (a |an )?"
                            + OBJECT_REGEX
                            + "\\.");

    public DomainRangeRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }

    @Override
    protected List<RuleVariant> buildRuleVariants() throws MappingToUmlTranslationFailedException {
        RuleVariant correct = new RuleVariant();
        correct.setSubjectTriplets(addPostfixToAllVariables(subjectTriplets, "C"));
        correct.setObjectTriplets(addPostfixToAllVariables(objectTriplets, "C"));
        correct.copyPredicate(predicate);

        RuleVariant wrong = new RuleVariant();
        ColoredTriplet triplet = getTripletWithBaseType(subjectTriplets.get(0));
        wrong.setSubjectTriplets(addPostfixToAllVariables(Arrays.asList(triplet), "W"));
        wrong.setObjectTriplets(addPostfixToAllVariables(objectTriplets, "W"));
        wrong.copyPredicate(predicate);

        correct.setPredicateToColorState(ColorState.CORRECT);
        wrong.setPredicateToColorState(ColorState.WRONG);
        return Arrays.asList(correct, wrong);
    }

    @Override
    protected Pattern getCnlPattern() {
        return CNL_PATTERN;
    }
}
