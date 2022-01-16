package org.archcnl.ui.outputview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.components.AbstractQueryResultsComponent;

public class ResultUpdateEvent extends ComponentEvent<AbstractQueryResultsComponent> {

    private static final long serialVersionUID = 1L;

    public ResultUpdateEvent(final AbstractQueryResultsComponent source, final boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
