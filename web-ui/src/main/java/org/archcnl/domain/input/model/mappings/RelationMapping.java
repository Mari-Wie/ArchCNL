package org.archcnl.domain.input.model.mappings;

import java.util.List;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;

public class RelationMapping extends Mapping {

    private Triplet thenTriplet;

    private CustomRelation thisRelation;

    public RelationMapping(
            Variable subject,
            ObjectType object,
            List<AndTriplets> whenTriplets,
            CustomRelation thisRelation)
            throws UnsupportedObjectTypeInTriplet {
        super(whenTriplets);
        this.thisRelation = thisRelation;
        thenTriplet = new Triplet(subject, thisRelation, object);
    }

    public void updateSubjectInThenTriplet(Variable subject) {
        thenTriplet.setSubject(subject);
    }

    public void updateObjectInThenTriplet(ObjectType object) throws UnsupportedObjectTypeInTriplet {
        thenTriplet.setObject(object);
    }

    @Override
    public Triplet getThenTriplet() {
        return thenTriplet;
    }

    @Override
    public String getMappingNameRepresentation() {
        return thisRelation.getName() + "Mapping";
    }
}
