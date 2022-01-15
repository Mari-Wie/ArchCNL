package org.archcnl.domain.input.model.mappings;

import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class ConceptMapping extends Mapping {

    private Triplet thenTriplet;

    public ConceptMapping(
            final Variable thenVariable,
            final List<AndTriplets> whenTriplets,
            final CustomConcept thisConcept)
            throws UnsupportedObjectTypeInTriplet, RelationDoesNotExistException {
        super(whenTriplets);
        final Optional<Relation> relationOpt =
                RulesConceptsAndRelations.getInstance()
                        .getRelationManager()
                        .getRelationByName("is-of-type");
        if (relationOpt.isEmpty()) {
            throw new RelationDoesNotExistException("is-of-type");
        }
        thenTriplet = TripletFactory.createTriplet(thenVariable, relationOpt.get(), thisConcept);
    }

    public void updateThenTriplet(final Variable subject) throws UnsupportedObjectTypeInTriplet {
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
