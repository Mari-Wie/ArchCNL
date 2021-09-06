package org.archcnl.domain.input.exceptions;

public class NoRelationException extends Exception {

    private static final long serialVersionUID = 7776947004605144543L;

    public NoRelationException(String potentialRelation) {
        super("The string \"" + potentialRelation + "\" could not be parsed to a relation.");
    }
}
