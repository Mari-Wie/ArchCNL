package org.archcnl.domain.input.visualization.rules;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.IsARelation;
import org.archcnl.domain.input.visualization.helpers.RuleHelper;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

public class FactRuleVisualizer extends RuleVisualizer {

    private static final Pattern IS_A_REGEX =
            Pattern.compile(
                    "Fact: (?<isaSubject>[A-Z][a-zA-Z]*) is (a |an )?(?<isaObject>[A-Z][a-zA-Z]*)\\.");
    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "(Fact: " + SUBJECT_REGEX + " " + PHRASES_REGEX + "\\.|" + IS_A_REGEX + ")");

    public FactRuleVisualizer(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        if (isAnIsAFact()) {
            System.out.println(ruleString);
            System.out.println(IS_A_REGEX);
            Matcher matcher = IS_A_REGEX.matcher(ruleString);
            RuleHelper.tryToFindMatch(matcher);
            subjectTriplets =
                    parseConceptExpression(
                            matcher.group("isaSubject"), Optional.empty(), Optional.empty());
            var objectTriplets =
                    parseConceptExpression(
                            matcher.group("isaObject"), Optional.empty(), Optional.empty());
            var predicate = new RulePredicate(new IsARelation());
            verbPhrases = new VerbPhraseContainer();
            verbPhrases.addVerbPhrase(new VerbPhrase(predicate, objectTriplets));
        } else {
            super.parseRule(ruleString);
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
        return Arrays.asList(variant);
    }

    private boolean isAnIsAFact() {
        return cnlString.contains(" is ");
    }

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }

    @Override
    protected Pattern getCnlPattern() {
        return CNL_PATTERN;
    }
}
