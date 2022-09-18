package org.archcnl.domain.input.visualization.elements.containers;

public enum VisibilityModifier {
    PRIVATE('-'),
    PROTECTED('#'),
    PUBLIC('+'),
    DEFAULT('~');

    private final char visibilityCharacter;

    private VisibilityModifier(char visibilityCharacter) {
        this.visibilityCharacter = visibilityCharacter;
    }

    public String getVisibilityPrefix() {
        return String.valueOf(visibilityCharacter);
    }
}
