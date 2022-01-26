package org.archcnl.domain.input.model.presets;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class ArchitecturalStyle {

    @JsonProperty("style")
    private ArchitecturalStyles style;

    @JsonProperty("architectureInformation")
    private List<ArchitectureInformation> variableParts = new ArrayList<ArchitectureInformation>();

    @JsonProperty("architectureRules")
    private List<ArchitectureRuleString> rules = new ArrayList<ArchitectureRuleString>();

    @JsonProperty("style")
    public ArchitecturalStyles getStyle() {
        return style;
    }

    @JsonProperty("style")
    public void setStyle(ArchitecturalStyles name) {
        this.style = name;
    }

    @JsonProperty("architectureInformation")
    public List<ArchitectureInformation> getVariableParts() {
        return variableParts;
    }

    @JsonProperty("architectureInformation")
    public void setVariableParts(List<ArchitectureInformation> variableParts) {
        this.variableParts = variableParts;
    }

    @JsonProperty("architectureRules")
    public List<ArchitectureRuleString> getRules() {
        return rules;
    }

    @JsonProperty("architectureRules")
    public void setRules(List<ArchitectureRuleString> rules) {
        this.rules = rules;
    }
}
