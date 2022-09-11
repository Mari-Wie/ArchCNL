package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;

public abstract class ClassContent extends NamedEntity {

    private static final String ABSTRACT_MODIFIER = "{abstract}";
    private static final String STATIC_MODIFIER = "{static}";

    private Optional<String> hasDeclaredType = Optional.empty();
    private Optional<VisibilityModifier> visibilityModifer = Optional.empty();
    private boolean isAbstract = false;
    private boolean isStatic = false;
    // final keyword is hard to model with available data

    protected ClassContent(String variableName) {
        super(variableName);
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

    protected String buildNameSection() {
        return getHighestRankingName();
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
