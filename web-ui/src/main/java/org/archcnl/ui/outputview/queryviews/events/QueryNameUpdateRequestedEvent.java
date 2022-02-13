package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class QueryNameUpdateRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -3248083198561810510L;
    private String name;

    public QueryNameUpdateRequestedEvent(Component source, boolean fromClient, String name) {
        super(source, fromClient);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
