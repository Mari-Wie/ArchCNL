package org.archcnl.ui.output.events;

import org.archcnl.ui.output.component.WhereTextBoxesLayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class AddWhereLayoutRequestEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = 1L;

    public AddWhereLayoutRequestEvent(final  Component addRemoveButtonLayout, final boolean fromClient) {
        super(addRemoveButtonLayout, fromClient);
        // TODO add logger call for event creation
    }
}
