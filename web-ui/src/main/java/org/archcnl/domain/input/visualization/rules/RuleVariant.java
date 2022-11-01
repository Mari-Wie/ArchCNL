package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

public class RuleVariant {

    private List<ColoredTriplet> subjectTriplets = new ArrayList<>();
    private List<ColoredTriplet> objectTriplets = new ArrayList<>();
    private Optional<RulePredicate> predicate = Optional.empty();
    private Optional<RulePredicate> secondaryPredicate = Optional.empty();

    public void setSubjectTriplets(List<ColoredTriplet> subjectTriplets) {
        this.subjectTriplets = subjectTriplets;
    }

    public void setObjectTriplets(List<ColoredTriplet> objectTriplets) {
        this.objectTriplets = objectTriplets;
    }

    public void copyPredicate(RulePredicate predicate) {
        this.predicate = Optional.of(predicate.copy());
    }

    public void setSecondaryPredicate(RulePredicate secondaryPredicate) {
        this.secondaryPredicate = Optional.of(secondaryPredicate);
    }

    public void setSubjectToColorState(ColorState state) {
        subjectTriplets.forEach(t -> t.setColorState(state));
    }

    public void setObjectToColorState(ColorState state) {
        objectTriplets.forEach(t -> t.setColorState(state));
    }

    public void setPredicateToColorState(ColorState state) {
        predicate.get().setColorState(state);
    }

    public List<ColoredTriplet> buildRuleTriplets() {
        List<ColoredTriplet> ruleTriplets = new ArrayList<>();
        ruleTriplets.addAll(subjectTriplets);
        ruleTriplets.addAll(objectTriplets);
        if (predicate.isPresent()) {
            ruleTriplets.add(predicate.get().getTriplet(getSubjectVariable(), getObjectVariable()));
        }
        if (secondaryPredicate.isPresent()) {
            ruleTriplets.add(
                    secondaryPredicate.get().getTriplet(getSubjectVariable(), getObjectVariable()));
        }
        return ruleTriplets;
    }

    private Variable getSubjectVariable() {
        return subjectTriplets.get(0).getSubject();
    }

    private Variable getObjectVariable() {
        return objectTriplets.get(0).getSubject();
    }
}
