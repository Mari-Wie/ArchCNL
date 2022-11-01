package org.archcnl.domain.input.visualization.rules;

import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

public class RulePredicate {

    private Relation predicate;
    private Optional<Cardinality> cardinality = Optional.empty();
    private Optional<Integer> quantity = Optional.empty();
    private Optional<ColorState> colorState = Optional.empty();

    public RulePredicate(Relation relation) {
        this.predicate = relation;
    }

    public ColoredTriplet getTriplet(Variable subject, Variable object) {
        var triplet = new ColoredTriplet(subject, predicate, object);
        if (colorState.isPresent()) {
            triplet.setColorState(colorState.get());
        }
        if (cardinality.isPresent() && quantity.isPresent()) {
            triplet.setCardinality(cardinality.get());
            triplet.setQuantity(quantity.get());
        }
        return triplet;
    }

    public void setColorState(ColorState state) {
        this.colorState = Optional.of(state);
    }

    public void setCardinalityLimitations(Cardinality cardinality, int quantity) {
        this.cardinality = Optional.of(cardinality);
        this.quantity = Optional.of(quantity);
    }

    public RulePredicate copy() {
        RulePredicate rulePredicate = new RulePredicate(predicate);
        rulePredicate.cardinality = this.cardinality;
        rulePredicate.quantity = this.quantity;
        rulePredicate.colorState = this.colorState;
        return rulePredicate;
    }

    public void invertLimitations() {
        cardinality = Optional.of(cardinality.get().getInverse());
    }

    public Relation getRelation() {
        return predicate;
    }
}
