package org.archcnl.domain.input.visualization.mapping;

public enum ColorState {
    NEUTRAL("#Black"),
    CORRECT("#RoyalBlue"),
    WRONG("#OrangeRed");

    private final String colorName;

    private ColorState(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }
}
