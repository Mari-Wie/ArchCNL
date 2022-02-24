package org.archcnl.domain.input.model.mappings;

import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.TripletFactory;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeInTriplet;

public class RelationMapping extends Mapping {

    private Triplet thenTriplet;

    public RelationMapping(final Triplet thenTriplet, final List<AndTriplets> whenTriplets) {
        super(whenTriplets);
        this.thenTriplet = thenTriplet;
    }

    public void updateSubjectInThenTriplet(final Variable subject)
            throws UnsupportedObjectTypeInTriplet {

        this.thenTriplet =
                TripletFactory.createTriplet(
                        subject, thenTriplet.getPredicate(), thenTriplet.getObject());
    }

    public void updateObjectInThenTriplet(final ObjectType object)
            throws UnsupportedObjectTypeInTriplet {
        this.thenTriplet =
                TripletFactory.createTriplet(
                        thenTriplet.getSubject(), thenTriplet.getPredicate(), object);
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
