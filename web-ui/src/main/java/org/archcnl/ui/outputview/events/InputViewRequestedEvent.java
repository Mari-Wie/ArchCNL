package org.archcnl.ui.outputview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.components.SideBarLayout;

public class InputViewRequestedEvent extends ComponentEvent<SideBarLayout> {

    private static final long serialVersionUID = 4180228788992640787L;

    public InputViewRequestedEvent(final SideBarLayout source, final boolean fromClient) {
        super(source, fromClient);
    }
}
