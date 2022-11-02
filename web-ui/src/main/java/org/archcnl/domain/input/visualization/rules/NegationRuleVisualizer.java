package org.archcnl.domain.input.visualization.rules;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

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
        tryToFindMatch(matcher);
        String phrasesGroup = matcher.group("phrases");
        verbPhrases = parseVerbPhrases(phrasesGroup);
        if (isNothingRule()) {
            subjectTriplets =
                    Arrays.asList(
                            getBaseSubjectTypeTriplet(
                                    verbPhrases.getPhrases().get(0).getPredicate().getRelation(),
                                    new Variable("nothing")));
        } else {
            subjectTriplets =
                    parseConceptExpression(
                            matcher.group("subject"), Optional.empty(), Optional.empty());
        }
    }

    @Override
    protected List<RuleVariant> buildRuleVariants() throws MappingToUmlTranslationFailedException {
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
