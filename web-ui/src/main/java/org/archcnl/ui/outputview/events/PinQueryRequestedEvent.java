package org.archcnl.ui.outputview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.components.CustomQueryPresenter;

public class PinQueryRequestedEvent extends ComponentEvent<CustomQueryPresenter> {

    private static final long serialVersionUID = 7470233750031587105L;

    public PinQueryRequestedEvent(CustomQueryPresenter source, boolean fromClient) {
        super(source, fromClient);
    }
}
