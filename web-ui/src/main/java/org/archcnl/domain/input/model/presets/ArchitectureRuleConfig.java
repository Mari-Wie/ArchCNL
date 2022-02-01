package org.archcnl.domain.input.model.presets;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ArchitectureRuleConfig {

    @JsonProperty private String rule;

    @JsonProperty private List<Integer> requiredArchitectureInformationIds;
    
    private boolean active = true;

    @JsonProperty
    public String getRule() {
        return rule;
    }

    @JsonProperty
    public void setRule(String rule) {
        this.rule = rule;
    }

    public List<Integer> getRequiredArchitectureInformationIds() {
        return requiredArchitectureInformationIds;
    }

    public void setRequiredArchitectureInformationIds(
            List<Integer> requiredArchitectureInformationIds) {
        this.requiredArchitectureInformationIds = requiredArchitectureInformationIds;
    }

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
