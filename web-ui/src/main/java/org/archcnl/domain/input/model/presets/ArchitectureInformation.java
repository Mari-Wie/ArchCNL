package org.archcnl.domain.input.model.presets;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchitectureInformation {

	@JsonProperty
	private int id;

	@JsonProperty
	private String name;

	@JsonProperty
	private int groupId;

	private String value;
	
	private boolean active = true;

	public ArchitectureInformation() {
	}

	@JsonProperty
	public int getId() {
		return id;
	}

	@JsonProperty
	public void setId(int id) {
		this.id = id;
	}

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
