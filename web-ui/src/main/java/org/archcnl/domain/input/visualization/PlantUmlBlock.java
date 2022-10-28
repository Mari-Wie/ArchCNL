package org.archcnl.domain.input.visualization;

import java.util.List;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.visualization.mapping.ColorState;

public interface PlantUmlBlock extends PlantUmlPart {

    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException;

    public boolean hasParentBeenFound();

    public List<String> getIdentifiers();

    public PlantUmlBlock createRequiredParentOrReturnSelf();

    public void setColorState(ColorState colorState);
}
