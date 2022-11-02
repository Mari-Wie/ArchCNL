package org.archcnl.domain.input.visualization.rules;

import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;

public class VerbPhrase {

    private RulePredicate predicate;
    private List<Triplet> objectTriplets;

    public VerbPhrase(RulePredicate predicate, List<Triplet> objectTriplets) {
        this.predicate = predicate;
        this.objectTriplets = objectTriplets;
    }

    public List<Triplet> getObjectTriplets() {
        return objectTriplets;
    }

    public RulePredicate getPredicate() {
        return predicate;
    }
}
