package org.archcnl.domain.input.model.mappings;

import java.util.List;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class ConceptMapping extends Mapping {

    private Triplet thenTriplet;

    private CustomConcept thisConcept;

    public ConceptMapping(
            Variable thenVariable, List<AndTriplets> whenTriplets, CustomConcept thisConcept)
            throws UnsupportedObjectTypeInTriplet, RelationDoesNotExistException {
        super(whenTriplets);
        this.thisConcept = thisConcept;
        thenTriplet =
                new Triplet(
                        thenVariable,
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type"),
                        thisConcept);
    }

    public void updateThenTriplet(Variable subject) {
        thenTriplet.setSubject(subject);
    }

    @Override
    public Triplet getThenTriplet() {
        return thenTriplet;
    }

    @Override
    public String getMappingNameRepresentation() {
        return "is" + thisConcept.getName();
    }
}
