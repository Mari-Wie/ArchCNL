package org.archcnl.domain.input.visualization.diagram.connections;

public enum ArrowType {
    BASIC(".."),
    CUSTOM_RELATION("-[bold]->"),
    SIMPLE_ARROW("-->"),
    INHERITS("--|>"),
    DASHED("-[dashed]->"),
    COMPOSITION("*--"),
    CONTAINMENT("+--");

    private String plantUmlCode;

    private ArrowType(String plantUmlCode) {
        this.plantUmlCode = plantUmlCode;
    }

    public String getPlantUmlCode() {
        return plantUmlCode;
    }
}
