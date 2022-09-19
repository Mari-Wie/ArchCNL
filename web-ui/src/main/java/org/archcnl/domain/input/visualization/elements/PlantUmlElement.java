package org.archcnl.domain.input.visualization.elements;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public abstract class PlantUmlElement {

    private boolean hasParent = false;
    private boolean requiresParent;
    protected Variable variable;

    protected PlantUmlElement(Variable variable, boolean requiresParent) {
        this.variable = variable;
        this.requiresParent = requiresParent;
    }

    public boolean hasRequiredParent() {
        return !requiresParent || hasParent;
    }

    public void parentIsFound() {
        this.hasParent = true;
    }

    public boolean hasParentBeenFound() {
        return hasParent;
    }

    public abstract String buildPlantUmlCode();

    public abstract void setProperty(String property, Object object)
            throws PropertyNotFoundException;
}
