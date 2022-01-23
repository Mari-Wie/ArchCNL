package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.queryviews.AbstractQueryResultsComponent;

public class CustomQueryInsertionRequestedEvent
        extends ComponentEvent<AbstractQueryResultsComponent> {

    private static final long serialVersionUID = 1L;

    public CustomQueryInsertionRequestedEvent(
            final AbstractQueryResultsComponent source, final boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
