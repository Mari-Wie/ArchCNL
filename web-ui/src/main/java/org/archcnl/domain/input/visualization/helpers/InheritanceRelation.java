package org.archcnl.domain.input.visualization.helpers;

import java.util.Collections;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.FamixRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class InheritanceRelation extends FamixRelation {

    private Optional<Variable> hasSubClass = Optional.empty();
    private Optional<Variable> hasSuperClass = Optional.empty();
    private ColorState colorState;

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
        triplet.setColorState(colorState);
        return triplet;
    }

    public void setColorState(ColorState colorState) {
        this.colorState = colorState;
    }
}
