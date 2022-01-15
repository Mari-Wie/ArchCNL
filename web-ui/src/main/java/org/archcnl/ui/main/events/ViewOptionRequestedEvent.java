package org.archcnl.ui.main.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.main.MainView;

public class ViewOptionRequestedEvent extends ComponentEvent<MainView> {

    private static final long serialVersionUID = -3711307621533570697L;

    public ViewOptionRequestedEvent(MainView source, boolean fromClient) {
        super(source, fromClient);
    }
}
