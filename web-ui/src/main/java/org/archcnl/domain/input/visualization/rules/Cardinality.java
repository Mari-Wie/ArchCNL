package org.archcnl.domain.input.visualization.rules;

import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public enum Cardinality {
    UNLIMITED(null),
    NOT_EXACTLY(UNLIMITED),
    NOT_AT_MOST(UNLIMITED),
    NOT_AT_LEAST(UNLIMITED),
    EXACTLY(NOT_EXACTLY),
    AT_MOST(NOT_AT_MOST),
    AT_LEAST(NOT_AT_LEAST);

    private final Cardinality inverse;

    private Cardinality(Cardinality inverse) {
        this.inverse = inverse;
    }

    public static Cardinality getCardinality(String cardinalityString)
            throws MappingToUmlTranslationFailedException {
        switch (cardinalityString) {
            case "exactly":
                return EXACTLY;
            case "at-most":
                return AT_MOST;
            case "at-least":
                return AT_LEAST;
            default:
                throw new MappingToUmlTranslationFailedException(
                        cardinalityString + " was not recognized.");
        }
    }

    public String buildSection(int quantity) {
        switch (this) {
            case NOT_EXACTLY:
                return String.format("\"<>%d\"", quantity);
            case NOT_AT_MOST:
                return String.format("\"%d..*\"", quantity + 1);
            case NOT_AT_LEAST:
                return String.format("\"0..%d\"", quantity - 1);
            case EXACTLY:
                return String.format("\"%d\"", quantity);
            case AT_MOST:
                return String.format("\"0..%d\"", quantity);
            case AT_LEAST:
                return String.format("\"%d..*\"", quantity);
            default:
                return "\"\"";
        }
    }

    public Cardinality getInverse() {
        return inverse;
    }
}
