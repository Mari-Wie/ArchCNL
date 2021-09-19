package org.archcnl.ui.input.mappingeditor.exceptions;

public class ObjectNotDefinedException extends Exception {

    private static final long serialVersionUID = 2893258139182968166L;

    public ObjectNotDefinedException() {
        super("The object is not defined.");
    }
}
