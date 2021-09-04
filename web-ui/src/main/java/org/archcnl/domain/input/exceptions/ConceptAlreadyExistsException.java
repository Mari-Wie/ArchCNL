package org.archcnl.domain.input.exceptions;

public class ConceptAlreadyExistsException extends Exception {

    private static final long serialVersionUID = -8412269206449816919L;

    public ConceptAlreadyExistsException(String name) {
        super("A concept with the name \"" + name + "\" already exists.");
    }
}
