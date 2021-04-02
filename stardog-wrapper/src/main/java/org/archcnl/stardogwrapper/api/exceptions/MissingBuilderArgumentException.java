package org.archcnl.stardogwrapper.api.exceptions;

public class MissingBuilderArgumentException extends Exception {
    private static final long serialVersionUID = -6652161850610357903L;

    public MissingBuilderArgumentException(String message) {
        super(message);
    }
}
