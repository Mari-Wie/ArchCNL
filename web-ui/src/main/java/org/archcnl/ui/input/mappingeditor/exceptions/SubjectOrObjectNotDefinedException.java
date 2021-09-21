package org.archcnl.ui.input.mappingeditor.exceptions;

public class SubjectOrObjectNotDefinedException extends Exception {

    private static final long serialVersionUID = 2605410854269957568L;

    public SubjectOrObjectNotDefinedException() {
        super("The subject/object is not defined.");
    }
}
