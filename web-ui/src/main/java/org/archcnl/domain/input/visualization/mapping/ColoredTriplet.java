package org.archcnl.domain.input.visualization.mapping;

import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class ColoredTriplet extends Triplet {

    private ColorState colorState = ColorState.NEUTRAL;

    public ColoredTriplet(Variable subject, Relation predicate, ObjectType object) {
        super(subject, predicate, object);
    }

    public ColoredTriplet(Triplet triplet) {
        super(triplet.getSubject(), triplet.getPredicate(), triplet.getObject());
    }

    public ColorState getColorState() {
        return colorState;
    }

    public void setColorState(ColorState colorState) {
        this.colorState = colorState;
    }

    public void setSubject(Variable subject) {
        this.subject = subject;
    }

    public void setPredicate(Relation predicate) {
        this.predicate = predicate;
    }

    public void setObject(ObjectType object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "(" + colorState + " " + super.toString() + ")";
    }
}
