package org.archcnl.ui.input.mappingeditor.exceptions;

public class ConceptNotDefinedException extends Exception {

    private static final long serialVersionUID = 2893258139182968166L;

    public ConceptNotDefinedException() {
        super("The object is not defined.");
    }
}
