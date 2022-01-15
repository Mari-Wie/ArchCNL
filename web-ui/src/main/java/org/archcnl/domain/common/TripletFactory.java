package org.archcnl.domain.common;

import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;

public class TripletFactory {
    public static Triplet createTriplet(
            final Variable subject, final Relation predicate, final ObjectType object)
            throws UnsupportedObjectTypeInTriplet {

        if (!predicate.canRelateToObjectType(object)) {
            throw new UnsupportedObjectTypeInTriplet(predicate, object);
        }

        return new Triplet(subject, predicate, object);
    }
}
