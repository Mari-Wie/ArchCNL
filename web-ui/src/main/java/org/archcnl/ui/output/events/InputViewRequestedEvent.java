package org.archcnl.ui.output.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.output.component.SideBarLayout;

public class InputViewRequestedEvent extends ComponentEvent<SideBarLayout> {

    private static final long serialVersionUID = 4180228788992640787L;

    public InputViewRequestedEvent(SideBarLayout source, boolean fromClient) {
        super(source, fromClient);
    }
}
