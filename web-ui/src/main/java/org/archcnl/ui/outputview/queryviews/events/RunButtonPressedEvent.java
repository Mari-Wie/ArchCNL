package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.queryviews.CustomQueryView;

public class RunButtonPressedEvent extends ComponentEvent<CustomQueryView> {

    private static final long serialVersionUID = 2614884942938936225L;

    public RunButtonPressedEvent(CustomQueryView source, boolean fromClient) {
        super(source, fromClient);
    }
}
