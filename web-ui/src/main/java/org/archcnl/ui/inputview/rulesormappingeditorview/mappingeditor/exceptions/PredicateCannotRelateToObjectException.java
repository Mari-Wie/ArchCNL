package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.exceptions;

import org.archcnl.domain.common.ObjectType;

public class PredicateCannotRelateToObjectException extends Exception {

    private static final long serialVersionUID = -6509527108125252594L;

    public PredicateCannotRelateToObjectException(ObjectType object) {
        super("The selected predicate cannot relate to object \"" + object.getName() + "\".");
    }
}
