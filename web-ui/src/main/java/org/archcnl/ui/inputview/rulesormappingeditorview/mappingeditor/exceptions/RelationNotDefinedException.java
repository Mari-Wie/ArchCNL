package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.exceptions;

public class RelationNotDefinedException extends Exception {

    private static final long serialVersionUID = -5241156947457001884L;

    public RelationNotDefinedException() {
        super("The predicate is not defined.");
    }
}
