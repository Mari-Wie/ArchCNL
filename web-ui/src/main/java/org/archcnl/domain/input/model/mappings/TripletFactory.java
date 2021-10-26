package org.archcnl.domain.input.model.mappings;

import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;

public class TripletFactory {
    public static Triplet createTriplet(Variable subject, Relation predicate, ObjectType object)
            throws UnsupportedObjectTypeInTriplet {

        if (!predicate.canRelateToObjectType(object)) {
            throw new UnsupportedObjectTypeInTriplet(predicate, object);
        }

        return new Triplet(subject, predicate, object);
    }
}
