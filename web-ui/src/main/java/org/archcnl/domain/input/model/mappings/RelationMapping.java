package org.archcnl.domain.input.model.mappings;

import java.util.List;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;

public class RelationMapping extends Mapping {

    private Triplet thenTriplet;

    public RelationMapping(Triplet triplet, List<AndTriplets> whenTriplets)
            throws UnsupportedObjectTypeInTriplet {
        super(whenTriplets);
        thenTriplet = triplet;
    }

    public void updateSubjectInThenTriplet(Variable subject) throws UnsupportedObjectTypeInTriplet {

        this.thenTriplet =
                new Triplet(subject, thenTriplet.getPredicate(), thenTriplet.getObject());
    }

    public void updateObjectInThenTriplet(ObjectType object) throws UnsupportedObjectTypeInTriplet {
        this.thenTriplet =
                new Triplet(thenTriplet.getSubject(), thenTriplet.getPredicate(), object);
    }

    @Override
    public Triplet getThenTriplet() {
        return thenTriplet;
    }

    @Override
    public String getMappingNameRepresentation() {
        return thenTriplet.getPredicate().getName() + "Mapping";
    }
}
