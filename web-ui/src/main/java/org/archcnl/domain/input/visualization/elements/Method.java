package org.archcnl.domain.input.visualization.elements;

import java.util.ArrayList;
import java.util.List;

public class Method extends ClassContent implements PlantUmlElement {
	
	private static final String METHOD_MODIFIER = "{method}";
	
	private List<Parameter> definesParameters = new ArrayList<>();

	/**
	 * Constructs a minimal method entry
	 * @param variableName Name of the variable that this method is bound to in the mapping
	 */
	protected Method(String variableName) {
		super(variableName);
	}

	@Override
	public String buildPlantUmlCode() {
		StringBuilder builder = new StringBuilder();
		builder.append(buildModifierSection());
		builder.append(buildNameSection());
		builder.append(buildParameterSection());
		builder.append(buildTypeSection());
		return builder.toString();
	}

	private String buildParameterSection() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		
		builder.append(")");
		return builder.toString();
	}

	@Override
	protected String getContentTypeModifier() {
		return METHOD_MODIFIER;
	}

}
