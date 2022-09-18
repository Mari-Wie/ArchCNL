package org.archcnl.domain.input.visualization.elements;

public enum VisibilityModifier {
    PRIVATE('-'),
    PROTECTED('#'),
    PUBLIC('+'),
    DEFAULT('~');

    private final char visibilityCharacter;

    private VisibilityModifier(char visibilityCharacter) {
        this.visibilityCharacter = visibilityCharacter;
    }

    public char getVisibilityCharacter() {
        return visibilityCharacter;
    }
}
