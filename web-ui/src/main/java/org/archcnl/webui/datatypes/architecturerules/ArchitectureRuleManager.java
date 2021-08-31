package org.archcnl.webui.datatypes.architecturerules;

import java.util.LinkedList;
import java.util.List;

public class ArchitectureRuleManager {

    private List<ArchitectureRule> architectureRules;

    public ArchitectureRuleManager() {
        architectureRules = new LinkedList<>();
    }

    public void addArchitectureRule(ArchitectureRule architectureRule) {
        architectureRules.add(architectureRule);
    }

    public void addAllArchitectureRules(List<ArchitectureRule> architectureRules) {
        for (ArchitectureRule rule : architectureRules) {
            addArchitectureRule(rule);
        }
    }

    public void deleteArchitectureRule(ArchitectureRule architectureRule) {
        architectureRules.remove(architectureRule);
    }

    public List<ArchitectureRule> getArchitectureRules() {
        return architectureRules;
    }
}
