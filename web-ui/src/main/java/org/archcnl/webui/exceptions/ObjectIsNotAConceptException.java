package org.archcnl.webui.exceptions;

public class ObjectIsNotAConceptException extends Exception {

    private static final long serialVersionUID = 4547961756100195713L;

    public ObjectIsNotAConceptException() {
        super("The object of this triplet is a variable and not a concept.");
    }
}
