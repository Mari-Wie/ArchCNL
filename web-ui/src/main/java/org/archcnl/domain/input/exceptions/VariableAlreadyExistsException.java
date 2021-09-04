package org.archcnl.domain.input.exceptions;

public class VariableAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 4800671712091523780L;

    public VariableAlreadyExistsException(String name) {
        super("The variable \"" + name + "\" already exists.");
    }
}
