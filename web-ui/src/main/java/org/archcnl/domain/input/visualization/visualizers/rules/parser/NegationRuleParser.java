package org.archcnl.domain.input.visualization.visualizers.rules.parser;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.visualizers.rules.NegationRuleVisualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.RuleHelper;
import org.archcnl.domain.input.visualization.visualizers.rules.RuleVisualizer;

public class NegationRuleParser extends RuleParser {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "(Nothing|No (a |an )?"
                            + SUBJECT_REGEX
                            + ")"
                            + " can "
                            + PHRASES_REGEX
                            + "\\.");

    protected NegationRuleParser(
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

    public static boolean matches(ArchitectureRule rule) {
        return CNL_PATTERN.matcher(rule.toString()).matches();
    }

    @Override
    protected Pattern getCnlPattern() {
        return CNL_PATTERN;
    }

    private boolean isNothingRule() {
        return cnlString.startsWith("Nothing");
    }

    @Override
    protected RuleVisualizer getRuleVisualizer() throws MappingToUmlTranslationFailedException {
        return new NegationRuleVisualizer(
                cnlString,
                subjectTriplets,
                verbPhrases,
                conceptManager,
                relationManager,
                usedVariables);
    }
}
