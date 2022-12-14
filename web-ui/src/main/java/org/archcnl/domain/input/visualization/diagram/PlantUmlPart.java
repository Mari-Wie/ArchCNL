package org.archcnl.domain.input.visualization.diagram;

import org.archcnl.domain.input.visualization.coloredmodel.ColorState;

public interface PlantUmlPart {

    public String buildPlantUmlCode();

    public void setColorState(ColorState colorState);
}
