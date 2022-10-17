package org.archcnl.domain.input.visualization;

import java.util.List;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public interface PlantUmlBlock extends PlantUmlPart {

    public void setProperty(String property, Object object) throws PropertyNotFoundException;

    public boolean hasParentBeenFound();

    public boolean hasRequiredParent();

    public List<String> getIdentifiers();
}
