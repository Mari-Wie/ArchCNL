package org.archcnl.ui.common.andtriplets.triplet.exceptions;

public class ObjectNotDefinedException extends Exception {

    private static final long serialVersionUID = 2893258139182968166L;

    public ObjectNotDefinedException() {
        super("The object is not defined.");
    }
}
