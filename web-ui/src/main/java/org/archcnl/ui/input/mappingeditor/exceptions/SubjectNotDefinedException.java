package org.archcnl.ui.input.mappingeditor.exceptions;

public class SubjectNotDefinedException extends Exception {

    private static final long serialVersionUID = 2605410854269957568L;

    public SubjectNotDefinedException() {
        super("The subject is not defined.");
    }
}
