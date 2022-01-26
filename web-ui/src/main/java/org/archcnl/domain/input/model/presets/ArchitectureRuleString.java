package org.archcnl.domain.input.model.presets;

import com.fasterxml.jackson.annotation.JsonProperty;

/** This is a helper class to use when reading values from json files with Jackson. This is */
public class ArchitectureRuleString {

    @JsonProperty private String rule;

    @JsonProperty
    public String getRule() {
        return rule;
    }

    @JsonProperty
    public void setRule(String rule) {
        this.rule = rule;
    }
}
