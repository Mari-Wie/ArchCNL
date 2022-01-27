package org.archcnl.domain.input.model.presets;

public enum ArchitecturalStyles {
    MICROSERVICE_ARCHITECTURE("Microservice Architecture"),
    LAYERED_ARCHITECTURE("Layered Architecture");

    private String architecturalStyleName;

    ArchitecturalStyles(String name) {
        this.architecturalStyleName = name;
    }

    @Override
    public String toString() {
        return architecturalStyleName;
    }
}
