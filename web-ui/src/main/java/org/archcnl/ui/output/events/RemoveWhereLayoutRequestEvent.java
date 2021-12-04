package org.archcnl.ui.output.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class RemoveWhereLayoutRequestEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = 1L;

    public RemoveWhereLayoutRequestEvent(final Component destination, final boolean fromClient) {
        super(destination, fromClient);
        // TODO add logger call for event creation
    }
}
