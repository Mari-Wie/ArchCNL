package org.archcnl.domain.common;

import java.util.Optional;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;

public class TripletFactory {
    public static Triplet createTriplet(
            Variable subject, Optional<Relation> predicate, ObjectType object)
            throws UnsupportedObjectTypeInTriplet {

        if (predicate.isEmpty() || !predicate.get().canRelateToObjectType(object)) {
            throw new UnsupportedObjectTypeInTriplet(predicate.orElseGet(null), object);
        }

        return new Triplet(subject, predicate.get(), object);
    }
}
