package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class PinQueryRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = 7470233750031587105L;
    private Component linkedComponent;
    private String queryName;

    public PinQueryRequestedEvent(
            Component source, boolean fromClient, Component linkedComponent, String queryName) {
        super(source, fromClient);
        this.linkedComponent = linkedComponent;
        this.queryName = queryName;
    }

    public Component getLinkedComponent() {
        return linkedComponent;
    }

    public String getQueryName() {
        return queryName;
    }
}
