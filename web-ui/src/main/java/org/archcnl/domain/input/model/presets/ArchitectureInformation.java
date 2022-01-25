package org.archcnl.domain.input.model.presets;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchitectureInformation {

    @JsonProperty private String name;

    @JsonProperty private int groupId;

    private String value;

    public ArchitectureInformation() {}

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public int getGroupId() {
        return groupId;
    }

    @JsonProperty
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
