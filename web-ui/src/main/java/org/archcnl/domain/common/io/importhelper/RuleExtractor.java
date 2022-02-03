package org.archcnl.domain.common.io.importhelper;

import com.google.common.annotations.VisibleForTesting;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.common.io.AdocIoUtils;
import org.archcnl.domain.input.exceptions.NoArchitectureRuleException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;

public class RuleExtractor {

    private static final Logger LOG = LogManager.getLogger(RuleExtractor.class);

    private static final Pattern RULE_CONTENT_PATTERN =
            Pattern.compile("(?<=\\[role=\"rule\"\\](\r\n?|\n)).+\\.");

    private RuleExtractor() {}

    public static List<ArchitectureRule> extractRules(String fileContent) {
        List<ArchitectureRule> rules = new LinkedList<>();

        AdocIoUtils.getAllMatches(RuleExtractor.RULE_CONTENT_PATTERN, fileContent).stream()
                .forEach(
                        potentialRule -> {
                            try {
                                rules.add(parseArchitectureRule(potentialRule));
                            } catch (final NoArchitectureRuleException e) {
                                RuleExtractor.LOG.warn(e.getMessage());
                            }
                        });

        return rules;
    }

    @SuppressWarnings("unused")
    private static ArchitectureRule parseArchitectureRule(final String potentialRule)
            throws NoArchitectureRuleException {
        if (false) {
            // TODO implement actual parsing once data model for rules exists
            throw new NoArchitectureRuleException(potentialRule);
        }
        return new ArchitectureRule(potentialRule);
    }

    @VisibleForTesting
    public static Pattern getRuleContentPattern() {
        return RULE_CONTENT_PATTERN;
    }
}
