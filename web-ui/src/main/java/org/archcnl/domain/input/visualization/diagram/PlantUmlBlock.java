package org.archcnl.domain.input.visualization.diagram;

import java.util.List;
import org.archcnl.domain.input.visualization.diagram.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public interface PlantUmlBlock extends PlantUmlPart {

    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException;

    public boolean hasParentBeenFound();

    public List<String> getIdentifiers();

    public PlantUmlBlock createRequiredParentOrReturnSelf();
}
