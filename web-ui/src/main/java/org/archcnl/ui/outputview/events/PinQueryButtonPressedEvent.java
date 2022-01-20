package org.archcnl.ui.outputview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.components.CustomQueryView;

public class PinQueryButtonPressedEvent extends ComponentEvent<CustomQueryView> {

    private static final long serialVersionUID = 4452797597049552302L;

    public PinQueryButtonPressedEvent(CustomQueryView source, boolean fromClient) {
        super(source, fromClient);
    }
}
