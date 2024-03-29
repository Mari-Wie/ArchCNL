package org.archcnl.domain.input.visualization.diagram.elements;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class BooleanElement extends PlantUmlElement {

    private boolean value;

    public BooleanElement(BooleanValue booleanValue) {
        super(new Variable(String.valueOf(booleanValue.getValue())), true);
        this.value = booleanValue.getValue();
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String buildPlantUmlCode() {
        return String.valueOf(value);
    }

    @Override
    protected String buildNameSection() {
        return String.valueOf(value);
    }

    @Override
    protected PlantUmlElement createParent(String parentName) throws PropertyNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String buildColorSection() {
        throw new UnsupportedOperationException();
    }

    public boolean getValue() {
        return value;
    }
}
