package org.archcnl.ui.outputview.sidebar.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.sidebar.SideBarWidget;

public class ShowComponentRequestedEvent extends ComponentEvent<SideBarWidget> {

    private static final long serialVersionUID = 8495492793551524581L;
    private Component component;

    public ShowComponentRequestedEvent(
            SideBarWidget source, boolean fromClient, Component component) {
        super(source, fromClient);
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
