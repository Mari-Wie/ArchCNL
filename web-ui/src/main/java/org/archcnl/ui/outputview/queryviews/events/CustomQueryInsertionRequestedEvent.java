package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.queryviews.AbstractQueryComponent;

public class CustomQueryInsertionRequestedEvent extends ComponentEvent<AbstractQueryComponent> {

    private static final long serialVersionUID = 1L;

    public CustomQueryInsertionRequestedEvent(
            final AbstractQueryComponent source, final boolean fromClient) {
        super(source, fromClient);
    }
}
