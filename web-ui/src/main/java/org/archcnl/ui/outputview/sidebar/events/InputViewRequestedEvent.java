package org.archcnl.ui.outputview.sidebar.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.sidebar.SideBarWidget;

public class InputViewRequestedEvent extends ComponentEvent<SideBarWidget> {

    private static final long serialVersionUID = 4180228788992640787L;

    public InputViewRequestedEvent(final SideBarWidget source, final boolean fromClient) {
        super(source, fromClient);
    }
}
