package org.archcnl.domain.input.visualization.helpers;

import java.util.Collections;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.FamixRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet.State;

public class InheritanceRelation extends FamixRelation {

    private Optional<Variable> hasSubClass = Optional.empty();
    private Optional<Variable> hasSuperClass = Optional.empty();
    private State state;

    public InheritanceRelation() {
        super(
                "inheritsFrom",
                "Helper Relation to replace Inheritance, hasSubClass, and hasSuperClass",
                Collections.singleton(new FamixConcept("FamixClass", "")),
                Collections.singleton(new FamixConcept("FamixClass", "")));
    }

    public void setHasSubClass(Variable subjectInTriplet) {
        this.hasSubClass = Optional.of(subjectInTriplet);
    }

    public void setHasSuperClass(Variable objectInTriplet) {
        this.hasSuperClass = Optional.of(objectInTriplet);
    }

    public ColoredTriplet buildInteritanceTriplet() throws MappingToUmlTranslationFailedException {
        if (hasSubClass.isEmpty() || hasSuperClass.isEmpty()) {
            throw new MappingToUmlTranslationFailedException(
                    "Inheritance relation not fully specified");
        }
        ColoredTriplet triplet = new ColoredTriplet(hasSubClass.get(), this, hasSuperClass.get());
        triplet.setState(state);
        return triplet;
    }

    public void setState(State state) {
        this.state = state;
    }
}
