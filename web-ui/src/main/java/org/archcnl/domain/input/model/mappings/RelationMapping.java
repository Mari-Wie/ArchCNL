package org.archcnl.domain.input.model.mappings;

import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;

public class RelationMapping extends Mapping {

    private Triplet thenTriplet;

    public RelationMapping(Triplet thenTriplet, List<AndTriplets> whenTriplets)
            throws UnsupportedObjectTypeInTriplet {
        super(whenTriplets);
        this.thenTriplet = thenTriplet;
    }

    public void updateSubjectInThenTriplet(Variable subject) throws UnsupportedObjectTypeInTriplet {

        this.thenTriplet =
                TripletFactory.createTriplet(
                        subject, Optional.of(thenTriplet.getPredicate()), thenTriplet.getObject());
    }

    public void updateObjectInThenTriplet(ObjectType object) throws UnsupportedObjectTypeInTriplet {
        this.thenTriplet =
                TripletFactory.createTriplet(
                        thenTriplet.getSubject(), Optional.of(thenTriplet.getPredicate()), object);
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
