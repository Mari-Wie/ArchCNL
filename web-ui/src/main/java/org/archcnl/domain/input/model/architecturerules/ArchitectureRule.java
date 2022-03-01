package org.archcnl.domain.input.model.architecturerules;

import java.util.Objects;
import org.archcnl.domain.common.FormattedAdocDomainObject;
import org.archcnl.domain.common.FormattedViewDomainObject;

public class ArchitectureRule implements FormattedAdocDomainObject, FormattedViewDomainObject {

    private String ruleString;

    public ArchitectureRule(String rule) {
        this.ruleString = rule;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    @Override
    public String transformToGui() {
        return ruleString;
    }

    @Override
    public String transformToAdoc() {
        return ruleString;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ArchitectureRule)) {
            return false;
        }
        ArchitectureRule otherRule = (ArchitectureRule) o;
        return transformToAdoc().equals(otherRule.transformToAdoc());
    }

    @Override
    public int hashCode() {
        return Objects.hash(transformToAdoc());
    }
}
