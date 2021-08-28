package org.archcnl.input.exceptions;

public class InvalidVariableNameException extends Exception {

    private static final long serialVersionUID = -973104923364933026L;

    public InvalidVariableNameException(String name) {
        super(
                "The variable name \""
                        + name
                        + "\" is invalid."
                        + "It must only contain word characters ([a-zA-Z0-9_]).");
    }
}
