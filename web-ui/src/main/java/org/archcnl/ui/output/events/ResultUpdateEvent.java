package org.archcnl.ui.output.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.output.component.AbstractQueryResults;

public class ResultUpdateEvent extends ComponentEvent<AbstractQueryResults> {

    private static final long serialVersionUID = 1L;

    public ResultUpdateEvent(final AbstractQueryResults source, final boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
