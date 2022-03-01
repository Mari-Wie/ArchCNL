package org.archcnl.domain.input.model.mappings;

import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class ConceptMapping extends Mapping {

    private Triplet thenTriplet;

    public ConceptMapping(
            final Variable thenVariable,
            final List<AndTriplets> whenTriplets,
            final CustomConcept thisConcept) {
        super(whenTriplets);
        thenTriplet = new Triplet(thenVariable, TypeRelation.getTyperelation(), thisConcept);
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
