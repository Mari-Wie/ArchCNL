package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;

public abstract class ClassContent {

	private static final String ABSTRACT_MODIFIER = "{abstract}";
	private static final String STATIC_MODIFIER = "{static}";
	
	private String variableName;
	
	private Optional<String> hasName = Optional.empty();
	private Optional<String> hasFullQualifiedName = Optional.empty();
	private Optional<String> hasDeclaredType = Optional.empty();
	private Optional<VisibilityModifier> visibilityModifer = Optional.empty();
	private boolean isAbstract = false;
	private boolean isStatic = false;
	// final is hard to model with available data
	
	protected ClassContent(String variableName) {
		this.variableName = variableName;
	}

	protected String buildModifierSection() {
		StringBuilder builder = new StringBuilder();
		builder.append(getContentTypeModifier() + " ");
		if (isAbstract) {
			builder.append(ABSTRACT_MODIFIER + " ");
		}
		if (isStatic) {
			builder.append(STATIC_MODIFIER + " ");
		}
		if (visibilityModifer.isPresent()) {
			builder.append(visibilityModifer.get().getVisibilityCharacter() + " ");
		}
		return builder.toString();
	}
	
	/**
	 * Builds the section containing the name of this field.
	 * The highest ranking available name is used:
	 * 1. Name linked by hasName
	 * 2. Name linked by hasFullQualifiedName
	 * 3. Name of the variable this individual is bound to
	 * @return The name representing this element
	 */
	protected String buildNameSection() {
		String nameSection = variableName;
		if (hasFullQualifiedName.isPresent()) {
			nameSection = hasFullQualifiedName.get();
		}
		if (hasName.isPresent()) {
			nameSection = hasName.get();
		}
		return nameSection;
	}
	
	protected String buildTypeSection() {
		String typeSection = "";
		if (hasDeclaredType.isPresent()) {
			typeSection = " : " + hasDeclaredType.get();
		}
		return typeSection;
	}
	
	protected abstract String getContentTypeModifier();
	
}
