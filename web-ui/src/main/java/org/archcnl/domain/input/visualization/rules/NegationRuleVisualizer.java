package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class NegationRuleVisualizer extends RuleVisualizer {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "(Nothing|No (a |an )?"
                            + SUBJECT_REGEX
                            + ")"
                            + " can "
                            + PHRASES_REGEX
                            + "\\.");

    public NegationRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        Matcher matcher = getCnlPattern().matcher(ruleString);
        RuleHelper.tryToFindMatch(matcher);
        String phrasesGroup = matcher.group("phrases");
        verbPhrases = parseVerbPhrases(phrasesGroup);
        if (isNothingRule()) {
            var nothingVar = new Variable("nothing");
            var relation = verbPhrases.getPhrases().get(0).getPredicate().getRelation();
            var triplet = RuleHelper.getBaseSubjectTypeTriplet(relation, nothingVar, usedVariables);
            subjectTriplets = Arrays.asList(triplet);
        } else {
            subjectTriplets = parseConceptExpression(matcher.group("subject"));
        }
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsAnd() {
        RuleVariant variant = new RuleVariant();
        variant.setSubjectTriplets(
                subjectTriplets.stream().map(ColoredTriplet::new).collect(Collectors.toList()));
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            variant.addObjectTriplets(
                    phrase.getObjectTriplets().stream()
                            .map(ColoredTriplet::new)
                            .collect(Collectors.toList()));
            variant.addCopyOfPredicate(phrase.getPredicate());
        }

        variant.setPredicateToColorState(ColorState.WRONG);
        return Arrays.asList(variant);
    }

    @Override
    protected List<RuleVariant> buildRuleVariantsOr() {
        StringBuilder postfix = new StringBuilder();
        List<RuleVariant> variants = new ArrayList<>();
        for (VerbPhrase phrase : verbPhrases.getPhrases()) {
            RuleVariant variant = new RuleVariant();

            variant.setSubjectTriplets(
                    addPostfixToAllVariables(subjectTriplets, postfix.toString()));
            variant.addObjectTriplets(
                    addPostfixToAllVariables(phrase.getObjectTriplets(), postfix.toString()));
            variant.addCopyOfPredicate(phrase.getPredicate());

            variant.setPredicateToColorState(ColorState.WRONG);

            variants.add(variant);
            postfix.append("1");
        }
        return variants;
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }

    private boolean isNothingRule() {
        return cnlString.startsWith("Nothing");
    }

    @Override
    protected Pattern getCnlPattern() {
        return CNL_PATTERN;
    }
}
