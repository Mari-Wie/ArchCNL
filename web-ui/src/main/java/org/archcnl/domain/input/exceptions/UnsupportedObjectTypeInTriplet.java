package org.archcnl.domain.input.exceptions;

public class UnsupportedObjectTypeInTriplet extends Exception {

    private static final long serialVersionUID = 5961111601469815136L;

    public UnsupportedObjectTypeInTriplet() {
        super("The predicate of this triplet does not support this type of object.");
    }
}
