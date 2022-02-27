package org.archcnl.domain.common.io.exporthelper;

import java.util.List;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;

public class RulesToStringTransformer {

    private RulesToStringTransformer() {}

    public static String constructArchRuleString(List<ArchitectureRule> rules) {
        StringBuilder builder = new StringBuilder();
        for (ArchitectureRule rule : rules) {
            builder.append("[role=\"rule\"]");
            builder.append("\n");
            builder.append(rule.transformToAdoc());
            builder.append("\n\n");
        }
        return builder.toString();
    }
}
