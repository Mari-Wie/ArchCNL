package org.archcnl.domain.input.model.architecturerules;

import java.time.LocalDate;
import java.util.Objects;
import org.archcnl.domain.common.FormattedAdocDomainObject;
import org.archcnl.domain.common.FormattedViewDomainObject;

public class ArchitectureRule implements FormattedAdocDomainObject, FormattedViewDomainObject {

    private String ruleString;
    private LocalDate validFrom;
    private LocalDate validUntil;

    public ArchitectureRule(String rule) {
        this.ruleString = rule;
    }

    public ArchitectureRule(String rule, LocalDate validFrom, LocalDate validUntil) {
        this.ruleString = rule;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
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
