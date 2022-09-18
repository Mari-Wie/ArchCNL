package org.archcnl.domain.input.visualization.elements;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class FamixEnum extends ClassOrEnum implements PlantUmlElement, FamixType, DeclaredType {

    public FamixEnum(Variable variable) {
        super(variable);
    }

    @Override
    protected String getElementIdentifier() {
        return "enum";
    }
}
