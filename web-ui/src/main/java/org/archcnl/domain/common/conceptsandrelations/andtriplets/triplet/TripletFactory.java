package org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet;

import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;

public class TripletFactory {
    public static Triplet createTriplet(
            final Variable subject, final Relation predicate, final ObjectType object)
            throws UnsupportedObjectTypeException {

        if (!predicate.canRelateToObjectType(object)) {
            throw new UnsupportedObjectTypeException(predicate, object);
        }

        return new Triplet(subject, predicate, object);
    }
}
