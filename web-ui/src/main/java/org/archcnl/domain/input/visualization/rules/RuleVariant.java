package org.archcnl.domain.input.visualization.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;

public class RuleVariant {

    private List<ColoredTriplet> subjectTriplets = new ArrayList<>();
    private List<List<ColoredTriplet>> objects = new ArrayList<>();
    private List<RulePredicate> predicates = new ArrayList<>();
    private Optional<RulePredicate> secondaryPredicate = Optional.empty();

    public void setSubjectTriplets(List<ColoredTriplet> subjectTriplets) {
        this.subjectTriplets = subjectTriplets;
    }

    public void addObjectTriplets(List<ColoredTriplet> objectTriplets) {
        this.objects.add(objectTriplets);
    }

    public void addCopyOfPredicate(RulePredicate predicate) {
        this.predicates.add(predicate.copy());
    }

    public void setCopyOfSecondaryPredicate(RulePredicate secondaryPredicate) {
        this.secondaryPredicate = Optional.of(secondaryPredicate.copy());
    }

    public void setSubjectToColorState(ColorState state) {
        subjectTriplets.forEach(t -> t.setColorState(state));
    }

    public void setObjectToColorState(ColorState state) {

        objects.forEach(o -> o.forEach(t -> t.setColorState(state)));
    }

    public void setPredicateToColorState(ColorState state) {
        predicates.forEach(p -> p.setColorState(state));
    }

    public List<ColoredTriplet> buildRuleTriplets() {
        List<ColoredTriplet> ruleTriplets = new ArrayList<>();
        ruleTriplets.addAll(subjectTriplets);
        for (int i = 0; i < objects.size(); i++) {
            List<ColoredTriplet> objectTriplets = objects.get(i);
            ruleTriplets.addAll(objectTriplets);
            Variable subjectVar = getSubjectVariable();
            Variable objectVar = getObjectVariable(objectTriplets);
            if (i < predicates.size()) {
                RulePredicate predicate = predicates.get(i);
                ruleTriplets.add(predicate.getTriplet(subjectVar, objectVar));
            }
            if (secondaryPredicate.isPresent()) {
                ruleTriplets.add(secondaryPredicate.get().getTriplet(subjectVar, objectVar));
            }
        }
        return ruleTriplets;
    }

    private Variable getSubjectVariable() {
        return subjectTriplets.get(0).getSubject();
    }

    private Variable getObjectVariable(List<ColoredTriplet> objectTriplets) {
        return objectTriplets.get(0).getSubject();
    }
}
