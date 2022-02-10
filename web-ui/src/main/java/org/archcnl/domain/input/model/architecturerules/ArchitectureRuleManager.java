package org.archcnl.domain.input.model.architecturerules;

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
        architectureRules.stream().forEach(this::addArchitectureRule);
    }

    public void deleteArchitectureRule(ArchitectureRule architectureRule) {
        architectureRules.remove(architectureRule);
    }

    public List<ArchitectureRule> getArchitectureRules() {
        return architectureRules;
    }

    public void updateArchitectureRule(ArchitectureRule oldRule, ArchitectureRule newRule) {
        architectureRules.stream()
                .filter(rule -> rule.equals(oldRule))
                .findFirst()
                .ifPresent(rule -> rule.setRuleString(newRule.toStringRepresentation()));
    }
}
