package org.archcnl.domain.input.model.mappings;

import java.util.List;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;

public class RelationMapping extends Mapping {

    private Triplet thenTriplet;

    private CustomRelation thisRelation;

    public RelationMapping(
            Variable subject,
            ObjectType object,
            List<AndTriplets> whenTriplets,
            CustomRelation thisRelation)
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet {
        super(whenTriplets);
        this.thisRelation = thisRelation;
        getVariableManager().addVariable(subject);
        thenTriplet = new Triplet(subject, thisRelation, object);
    }

    public void updateSubjectInThenTriplet(Variable subject) {
        if (!getVariableManager().doesVariableExist(subject)) {
            try {
                getVariableManager().addVariable(subject);
            } catch (VariableAlreadyExistsException e) {
                // Cannot occur
            }
        }
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
