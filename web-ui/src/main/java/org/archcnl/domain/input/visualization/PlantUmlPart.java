package org.archcnl.domain.input.visualization;

import org.archcnl.domain.input.visualization.mapping.ColorState;

public interface PlantUmlPart {

    public String buildPlantUmlCode();

    public void setColorState(ColorState colorState);
}
