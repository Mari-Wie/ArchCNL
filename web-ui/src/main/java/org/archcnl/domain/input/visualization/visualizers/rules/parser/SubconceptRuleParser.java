package org.archcnl.domain.input.visualization.visualizers.rules.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.IsARelation;
import org.archcnl.domain.input.visualization.visualizers.rules.Helper;
import org.archcnl.domain.input.visualization.visualizers.rules.RuleVisualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.SubconceptRuleVisualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.RulePredicate;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhrase;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.VerbPhraseContainer;

public class SubconceptRuleParser extends RuleParser {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Every "
                            + "(a |an )?"
                            + SUBJECT_REGEX
                            + " must be"
                            + " (a |an )?"
                            + OBJECT_REGEX
                            + "\\.");

    protected SubconceptRuleParser(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        Matcher matcher = getCnlPattern().matcher(ruleString);
        Helper.tryToFindMatch(matcher);
        var isAPredicate = new RulePredicate(new IsARelation());
        var objectTriplets = parseConceptExpression(matcher.group("object"));
        VerbPhrase phrase = new VerbPhrase(isAPredicate, objectTriplets);
        verbPhrases = new VerbPhraseContainer();
        verbPhrases.addVerbPhrase(phrase);
        subjectTriplets = parseConceptExpression(matcher.group("subject"));
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
        return new SubconceptRuleVisualizer(
                cnlString,
                subjectTriplets,
                verbPhrases,
                conceptManager,
                relationManager,
                usedVariables);
    }
}
