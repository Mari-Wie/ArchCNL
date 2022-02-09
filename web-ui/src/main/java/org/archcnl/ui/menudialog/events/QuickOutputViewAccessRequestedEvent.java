package org.archcnl.ui.menudialog.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.menudialog.SelectDirectoryDialog;

public class QuickOutputViewAccessRequestedEvent extends ComponentEvent<SelectDirectoryDialog> {

    private static final long serialVersionUID = 778098508985939073L;

    public QuickOutputViewAccessRequestedEvent(SelectDirectoryDialog source, boolean fromClient) {
        super(source, fromClient);
    }
}
