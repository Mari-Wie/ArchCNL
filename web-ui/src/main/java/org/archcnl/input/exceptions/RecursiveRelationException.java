package org.archcnl.input.exceptions;

public class RecursiveRelationException extends Exception {

    private static final long serialVersionUID = -9191582152550566998L;

    public RecursiveRelationException() {
        super("A variable cannot relate to itself.");
    }
}
