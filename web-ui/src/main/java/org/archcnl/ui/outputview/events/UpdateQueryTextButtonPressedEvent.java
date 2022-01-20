package org.archcnl.ui.outputview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.components.CustomQueryView;

public class UpdateQueryTextButtonPressedEvent extends ComponentEvent<CustomQueryView> {

    private static final long serialVersionUID = -5332348710277694769L;

    public UpdateQueryTextButtonPressedEvent(CustomQueryView source, boolean fromClient) {
        super(source, fromClient);
    }
}
