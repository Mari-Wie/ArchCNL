package org.archcnl.domain.input.visualization.elements;

public class Field extends ClassContent implements PlantUmlElement {
	
	private static final String FIELD_MODIFIER = "{field}";
	
	/**
	 * Constructs a minimal field entry
	 * @param variableName Name of the variable that this field is bound to in the mapping
	 */
	public Field(String variableName) {
		super(variableName);
	}

	@Override
	public String buildPlantUmlCode() {
		StringBuilder builder = new StringBuilder();
		builder.append(buildModifierSection());
		builder.append(buildNameSection());
		builder.append(buildTypeSection());
		return builder.toString();
	}

	@Override
	protected String getContentTypeModifier() {
		return FIELD_MODIFIER;
	}

}
