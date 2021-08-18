package org.archcnl.webui.datatypes.architecturerules;

import java.util.Objects;

public class ArchitectureRule {

    private String ruleString;

    public ArchitectureRule(String rule) {
        this.ruleString = rule;
    }

    public String toStringRepresentation() {
        return ruleString;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ArchitectureRule)) {
            return false;
        }
        ArchitectureRule otherRule = (ArchitectureRule) o;
        return toStringRepresentation().equals(otherRule.toStringRepresentation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toStringRepresentation());
    }
}
