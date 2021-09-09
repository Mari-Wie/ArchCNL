package org.archcnl.domain.input.exceptions;

import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.Relation;

public class UnsupportedObjectTypeInTriplet extends Exception {

    private static final long serialVersionUID = 5961111601469815136L;

    public UnsupportedObjectTypeInTriplet(Relation predicate, ObjectType object) {
        super(
                "The predicate \""
                        + predicate.getName()
                        + "\" of this triplet does not support the object type \""
                        + object.getClass().getSimpleName()
                        + "\".");
    }
}
