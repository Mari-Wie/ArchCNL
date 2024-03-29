package org.archcnl.domain.input.visualization.diagram.elements;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class FamixEnum extends ClassOrEnum implements FamixType {

    public FamixEnum(Variable variable) {
        super(variable);
    }

    @Override
    protected String getElementIdentifier() {
        return "enum";
    }

    @Override
    protected String buildParentSection() {
        return "";
    }
}
