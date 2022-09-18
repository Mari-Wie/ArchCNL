package org.archcnl.domain.input.visualization.elements;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class FamixClass extends ClassOrEnum implements PlantUmlElement, FamixType, DeclaredType {

    private boolean isInterface = false;

    public FamixClass(Variable variable) {
        super(variable);
    }

    @Override
    protected String getElementIdentifier() {
        return isInterface ? "interface" : "class";
    }

    @Override
    public void setProperty(String property, Object object) throws PropertyNotFoundException {
        if ("isInterface".equals(property)) {
            this.isInterface = (boolean) object;
        } else {
            super.setProperty(property, object);
        }
    }
}
