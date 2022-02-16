package org.archcnl.domain.input.model.presets;

public enum ArchitecturalStyle {
    MICROSERVICE_ARCHITECTURE("Microservice Architecture"),
    LAYERED_ARCHITECTURE("Layered Architecture");

    private String architecturalStyleName;

    ArchitecturalStyle(String name) {
        this.architecturalStyleName = name;
    }

    @Override
    public String toString() {
        return architecturalStyleName;
    }
}
