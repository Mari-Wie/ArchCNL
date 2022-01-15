package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.MainView;

public class ViewOptionRequestedEvent extends ComponentEvent<MainView> {

    private static final long serialVersionUID = -3711307621533570697L;

    public ViewOptionRequestedEvent(final MainView source, final boolean fromClient) {
        super(source, fromClient);
    }
}
