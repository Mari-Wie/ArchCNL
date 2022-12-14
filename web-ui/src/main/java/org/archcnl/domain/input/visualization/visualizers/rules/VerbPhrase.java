package org.archcnl.domain.input.visualization.visualizers.rules;

import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;

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

    public ColoredTriplet getTriplet(Variable subject) {
        return new ColoredTriplet(subject, predicate.getRelation(), getSubjectOfPhrase());
    }

    private Variable getSubjectOfPhrase() {
        return objectTriplets.get(0).getSubject();
    }
}
