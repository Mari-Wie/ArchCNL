package org.archcnl.domain.input.visualization.elements;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class StringElement extends PlantUmlElement {

    private String text;

    public StringElement(String text) {
        super(new Variable(text), true);
        this.text = text;
    }

    @Override
    public String buildPlantUmlCode() {
        return text;
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String buildNameSection() {
        return text;
    }

    @Override
    protected PlantUmlElement createParent(String parentName) throws PropertyNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return buildPlantUmlCode();
    }
}
