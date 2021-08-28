package org.archcnl.input.exceptions;

public class AllConceptsSupportedException extends Exception {

    private static final long serialVersionUID = 399367737994817092L;

    public AllConceptsSupportedException(String relationName) {
        super("The relation \"" + relationName + "\" supports all concepts as objects.");
    }
}
