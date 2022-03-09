package org.archcnl.domain.input.model.mappings;

import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;

public class RelationMapping extends Mapping {

    private Triplet thenTriplet;

    public RelationMapping(final Triplet thenTriplet, final List<AndTriplets> whenTriplets) {
        super(whenTriplets);
        this.thenTriplet = thenTriplet;
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
