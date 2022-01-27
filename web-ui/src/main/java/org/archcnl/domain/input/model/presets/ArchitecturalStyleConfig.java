package org.archcnl.domain.input.model.presets;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class ArchitecturalStyleConfig {

    @JsonProperty("style")
    private ArchitecturalStyle styleName;

    @JsonProperty("architectureInformation")
    private List<ArchitectureInformation> variableParts = new ArrayList<ArchitectureInformation>();

    @JsonProperty("architectureRules")
    private List<ArchitectureRuleString> rules = new ArrayList<ArchitectureRuleString>();

    @JsonProperty("style")
    public ArchitecturalStyle getStyleName() {
        return styleName;
    }

    @JsonProperty("style")
    public void setStyleName(ArchitecturalStyle name) {
        this.styleName = name;
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
