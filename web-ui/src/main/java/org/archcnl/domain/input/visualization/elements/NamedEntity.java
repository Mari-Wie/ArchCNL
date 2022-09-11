package org.archcnl.domain.input.visualization.elements;

import java.util.Optional;

public abstract class NamedEntity {

    private String variableName;

    private Optional<String> hasName = Optional.empty();
    private Optional<String> hasFullQualifiedName = Optional.empty();

    protected NamedEntity(String variableName) {
        this.variableName = variableName;
    }

    /**
     * Returns the name best suited to represent this entity. The highest ranking available name is
     * used: 1. Name linked by hasName 2. Name linked by hasFullQualifiedName 3. Name of the
     * variable this individual is bound to
     *
     * @return The name representing this element
     */
    protected String getHighestRankingName() {
        String nameSection = variableName;
        if (hasFullQualifiedName.isPresent()) {
            nameSection = hasFullQualifiedName.get();
        }
        if (hasName.isPresent()) {
            nameSection = hasName.get();
        }
        return nameSection;
    }

    public void setHasName(Optional<String> hasName) {
        this.hasName = hasName;
    }

    public void setHasFullQualifiedName(Optional<String> hasFullQualifiedName) {
        this.hasFullQualifiedName = hasFullQualifiedName;
    }
}
