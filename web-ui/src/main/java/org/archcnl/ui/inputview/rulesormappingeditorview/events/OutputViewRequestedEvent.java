package org.archcnl.ui.inputview.rulesormappingeditorview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class OutputViewRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -9209981613958108844L;

    public OutputViewRequestedEvent(final Component source, final boolean fromClient) {
        super(source, fromClient);
    }
}
