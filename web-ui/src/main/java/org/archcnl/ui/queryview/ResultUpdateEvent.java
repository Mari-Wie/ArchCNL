package org.archcnl.ui.queryview;

import com.vaadin.flow.component.ComponentEvent;

public class ResultUpdateEvent extends ComponentEvent<CustomQueryResults> {
    public ResultUpdateEvent(CustomQueryResults source, boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
