package org.archcnl.ui.output.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class AddWhereLayoutRequestEvent<T extends Component> extends ComponentEvent<T> {

    private static final long serialVersionUID = 1L;

    public AddWhereLayoutRequestEvent(final T addRemoveButtonLayout, final boolean fromClient) {
        super(addRemoveButtonLayout, fromClient);
        // TODO add logger call for event creation
    }
}
