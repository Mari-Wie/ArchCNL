package org.archcnl.domain.common.exceptions;

public class RelationAlreadyExistsException extends Exception {

    private static final long serialVersionUID = -6060711080914673795L;

    public RelationAlreadyExistsException(String name) {
        super("The relation \"" + name + "\" already exists.");
    }
}
