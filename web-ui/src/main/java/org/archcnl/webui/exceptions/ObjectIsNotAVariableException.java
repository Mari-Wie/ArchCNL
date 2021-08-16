package org.archcnl.webui.exceptions;

public class ObjectIsNotAVariableException extends Exception {

    private static final long serialVersionUID = -3502324060266525105L;

    public ObjectIsNotAVariableException() {
        super("The object of this triplet is a concept and not a variable.");
    }
}
