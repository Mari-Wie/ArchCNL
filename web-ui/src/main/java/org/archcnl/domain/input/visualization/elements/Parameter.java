package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;

public class Parameter extends NamedEntity implements PlantUmlElement {

    private Optional<String> hasDeclaredType = Optional.empty();

    public Parameter(String variableName) {
        super(variableName);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(buildNameSection());
        builder.append(buildTypeSection());
        return builder.toString();
    }

    private String buildNameSection() {
        return getHighestRankingName();
    }

    private String buildTypeSection() {
        StringBuilder builder = new StringBuilder();
        if (hasDeclaredType.isPresent()) {
            builder.append(":");
            builder.append(hasDeclaredType.get());
        }
        return builder.toString();
    }
}
