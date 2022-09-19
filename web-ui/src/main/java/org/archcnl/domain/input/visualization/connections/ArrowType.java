package org.archcnl.domain.input.visualization.connections;

public enum ArrowType {
    BASIC(".."),
    CUSTOM_RELATION("-[bold]->"),
    SIMPLE_ARROW("-->"),
    INHERITS("--|>");

    private String plantUmlCode;

    private ArrowType(String plantUmlCode) {
        this.plantUmlCode = plantUmlCode;
    }

    public String getPlantUmlCode() {
        return plantUmlCode;
    }
}
