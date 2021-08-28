package org.archcnl.input.exceptions;

public class VariableDoesNotExistException extends Exception {

    private static final long serialVersionUID = -8902817400202404029L;

    public VariableDoesNotExistException(String name) {
        super("The variable with the name \"" + name + "\" does not exist.");
    }
}
