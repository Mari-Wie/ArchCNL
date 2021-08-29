package org.archcnl.ui.queryview;

import com.vaadin.flow.component.ComponentEvent;

public class ResultUpdateEvent extends ComponentEvent<QueryResults> {

    private static final long serialVersionUID = 1L;

    public ResultUpdateEvent(final QueryResults source, final boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
