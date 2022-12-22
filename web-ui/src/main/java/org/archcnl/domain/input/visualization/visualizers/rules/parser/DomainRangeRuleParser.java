package org.archcnl.domain.input.visualization.visualizers.rules.parser;

import java.util.regex.Pattern;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.visualizers.rules.DomainRangeRuleVisualizer;
import org.archcnl.domain.input.visualization.visualizers.rules.RuleVisualizer;

public class DomainRangeRuleParser extends RuleParser {

    private static final Pattern CNL_PATTERN =
            Pattern.compile(
                    "Only " + "(a |an )?" + SUBJECT_REGEX + " can " + PHRASES_REGEX + "\\.");

    protected DomainRangeRuleParser(
            ArchitectureRule rule, ConceptManager conceptManager, RelationManager relationManager)
            throws MappingToUmlTranslationFailedException {
        super(rule, conceptManager, relationManager);
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
        return new DomainRangeRuleVisualizer(
                cnlString,
                subjectTriplets,
                verbPhrases,
                conceptManager,
                relationManager,
                usedVariables);
    }
}
