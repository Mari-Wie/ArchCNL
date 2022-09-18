package org.archcnl.domain.input.visualization.elements;

import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public interface PlantUmlElement {

    public String buildPlantUmlCode();

    public void setProperty(String property, Object object) throws PropertyNotFoundException;
}
