package org.archcnl.domain.input.model.mappings;

import java.util.List;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class ConceptMapping extends Mapping {

    private Triplet thenTriplet;

    private CustomConcept thisConcept;

    public ConceptMapping(
            Variable thenVariable, List<AndTriplets> whenTriplets, CustomConcept thisConcept)
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException {
        super(whenTriplets);
        getVariableManager().addVariable(thenVariable);
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
        if (!getVariableManager().doesVariableExist(subject)) {
            try {
                getVariableManager().addVariable(subject);
            } catch (VariableAlreadyExistsException e) {
                // Cannot occur
                throw new RuntimeException(
                        "Unexpected error during creation of variable \""
                                + subject.getName()
                                + "\".");
            }
        }
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
