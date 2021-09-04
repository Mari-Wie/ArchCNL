package org.archcnl.ui.output.events;

import org.archcnl.ui.output.component.CustomQueryUiComponent;
import com.vaadin.flow.component.ComponentEvent;

public class ResultUpdateEvent extends ComponentEvent<CustomQueryUiComponent> {

    private static final long serialVersionUID = 1L;

    public ResultUpdateEvent(final CustomQueryUiComponent source, final boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
