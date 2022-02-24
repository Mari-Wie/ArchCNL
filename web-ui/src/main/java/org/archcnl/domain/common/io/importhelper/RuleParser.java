package org.archcnl.domain.common.io.importhelper;

import com.google.common.annotations.VisibleForTesting;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.archcnl.domain.common.io.RegexUtils;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;

public class RuleParser {

    private static final Pattern RULE_CONTENT_PATTERN =
            Pattern.compile("(?<=\\[role=\"rule\"\\](\r\n?|\n)).+\\.");

    private RuleParser() {}

    public static List<ArchitectureRule> extractRules(String fileContent) {
        List<ArchitectureRule> rules = new LinkedList<>();

        RegexUtils.getAllMatches(RuleParser.RULE_CONTENT_PATTERN, fileContent).stream()
                .forEach(potentialRule -> rules.add(parseArchitectureRule(potentialRule)));

        return rules;
    }

    private static ArchitectureRule parseArchitectureRule(final String potentialRule) {
        return new ArchitectureRule(potentialRule);
    }

    @VisibleForTesting
    public static Pattern getRuleContentPattern() {
        return RULE_CONTENT_PATTERN;
    }
}
