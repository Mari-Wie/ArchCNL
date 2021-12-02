package org.archcnl.ui.output.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class RemoveWhereLayoutRequestEvent<T extends Component> extends ComponentEvent<T> {

    private static final long serialVersionUID = 1L;

    public RemoveWhereLayoutRequestEvent(
            final T destination, final boolean fromClient) {
        super(destination, fromClient);
        // TODO add logger call for event creation
    }
}
