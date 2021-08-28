package org.archcnl.input.exceptions;

public class RelationDoesNotExistException extends Exception {

    private static final long serialVersionUID = 8091854307644483619L;

    public RelationDoesNotExistException(String name) {
        super("The relation with the name \"" + name + "\" does not exist.");
    }
}
