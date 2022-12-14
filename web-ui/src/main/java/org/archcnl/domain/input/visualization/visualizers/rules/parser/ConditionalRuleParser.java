package org.archcnl.domain.input.visualization.visualizers.rules.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.visualizers.rules.ConditionalRuleVisualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.Helper;
import org.archcnl.domain.input.visualization.visualizers.rules.RuleVisualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.rulemodel.RulePredicate;

public class ConditionalRuleParser extends RuleParser {

    private static final String SECOND_PREDICATE_REGEX = "(?<predicate2>[a-z][a-zA-Z]*)";
    private static final String SECOND_OBJECT_REGEX = "(?<object2>[A-Z][a-zA-Z]*( that \\(.+\\))?)";
    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "If "
                            + "(a |an )?"
                            + SUBJECT_REGEX
                            + " "
                            + PHRASES_REGEX
                            + ", then it must "
                            + SECOND_PREDICATE_REGEX
                            + " this (a |an )?"
                            + SECOND_OBJECT_REGEX
                            + "\\.");

    private RulePredicate secondaryPredicate;

    protected ConditionalRuleParser(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
    }

    @Override
    protected void parseRule(String ruleString) throws MappingToUmlTranslationFailedException {
        Matcher matcher = CNL_PATTERN.matcher(ruleString);
        Helper.tryToFindMatch(matcher);
        if (!matcher.group("object").equals(matcher.group("object2"))) {
            throw new MappingToUmlTranslationFailedException(cnlString + " Has different objects.");
        }
        String phrasesGroup = matcher.group("phrases");
        verbPhrases = parseVerbPhrases(phrasesGroup);
        subjectTriplets = parseConceptExpression(matcher.group("subject"));
        String secondRelationName = matcher.group("predicate2");
        Relation secondRelation = Helper.getRelation(secondRelationName, relationManager);
        secondaryPredicate = new RulePredicate(secondRelation);
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
        return new ConditionalRuleVisualizer(
                cnlString,
                subjectTriplets,
                verbPhrases,
                secondaryPredicate,
                conceptManager,
                relationManager,
                usedVariables);
    }
}
