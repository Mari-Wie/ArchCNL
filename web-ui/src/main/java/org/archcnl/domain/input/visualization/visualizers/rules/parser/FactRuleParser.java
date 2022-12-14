package org.archcnl.domain.input.visualization.visualizers.rules.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.IsARelation;
import org.archcnl.domain.input.visualization.visualizers.rules.FactRuleVisualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.Helper;
import org.archcnl.domain.input.visualization.visualizers.rules.RuleVisualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.RulePredicate;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhrase;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhraseContainer;

public class FactRuleParser extends RuleParser {

    private static final Pattern IS_A_REGEX =
            Pattern.compile(
                    "Fact: (?<isaSubject>[A-Z][a-zA-Z]*) is (a |an )?(?<isaObject>[A-Z][a-zA-Z]*)\\.");
    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "(Fact: " + SUBJECT_REGEX + " " + PHRASES_REGEX + "\\.|" + IS_A_REGEX + ")");

    protected FactRuleParser(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        if (isAnIsAFact()) {
            Matcher matcher = IS_A_REGEX.matcher(ruleString);
            Helper.tryToFindMatch(matcher);
            subjectTriplets = parseConceptExpression(matcher.group("isaSubject"));
            var objectTriplets = parseConceptExpression(matcher.group("isaObject"));
            var predicate = new RulePredicate(new IsARelation());
            verbPhrases = new VerbPhraseContainer();
            verbPhrases.addVerbPhrase(new VerbPhrase(predicate, objectTriplets));
        } else {
            super.parseRule(ruleString);
        }
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

    @Override
    protected RuleVisualizer getRuleVisualizer() throws MappingToUmlTranslationFailedException {
        return new FactRuleVisualizer(
                cnlString,
                subjectTriplets,
                verbPhrases,
                conceptManager,
                relationManager,
                usedVariables);
    }
}
