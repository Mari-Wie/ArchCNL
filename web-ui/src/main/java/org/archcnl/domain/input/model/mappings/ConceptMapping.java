package org.archcnl.domain.input.model.mappings;

import java.util.List;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class ConceptMapping extends Mapping {

    private Triplet thenTriplet;

    public ConceptMapping(
            Variable thenVariable, List<AndTriplets> whenTriplets, CustomConcept thisConcept)
            throws UnsupportedObjectTypeInTriplet, RelationDoesNotExistException {
        super(whenTriplets);
        thenTriplet =
                TripletFactory.createTriplet(
                        thenVariable,
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type"),
                        thisConcept);
    }

    public void updateThenTriplet(Variable subject) throws UnsupportedObjectTypeInTriplet {

        this.thenTriplet =
                TripletFactory.createTriplet(
                        subject, thenTriplet.getPredicate(), thenTriplet.getObject());
    }

    @Override
    public Triplet getThenTriplet() {
        return thenTriplet;
    }

    @Override
    public String getMappingNameRepresentation() {
        return "is" + thenTriplet.getObject().getName();
    }
}
