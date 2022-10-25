package org.archcnl.domain.input.visualization.mapping;

import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class ColoredTriplet extends Triplet {

    public enum State {
        NEUTRAL,
        CORRECT,
        WRONG
    }

    private State state = State.NEUTRAL;

    public ColoredTriplet(Variable subject, Relation predicate, ObjectType object) {
        super(subject, predicate, object);
    }

    public ColoredTriplet(Triplet triplet) {
        super(triplet.getSubject(), triplet.getPredicate(), triplet.getObject());
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
        return "(" + state + " " + super.toString() + ")";
    }
}
