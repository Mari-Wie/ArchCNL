package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class DeleteButtonPressedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -1491958813766309100L;

    public DeleteButtonPressedEvent(Component source, boolean fromClient) {
        super(source, fromClient);
    }
}
