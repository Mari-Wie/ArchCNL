package org.archcnl.domain.input.model.mappings;

import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.mappings.Triplet;

import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.domain.input.model.mappings.Relation;

public class TripletFactory{
    public static Triplet createTriplet(Variable subject, Relation predicate, ObjectType object)
            throws UnsupportedObjectTypeInTriplet {

            if (!predicate.canRelateToObjectType(object)) {
                throw new UnsupportedObjectTypeInTriplet(predicate, object);
            }

            return new Triplet(subject, predicate, object);
    }
}

