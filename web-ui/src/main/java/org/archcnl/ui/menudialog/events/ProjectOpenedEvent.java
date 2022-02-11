package org.archcnl.ui.menudialog.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.menudialog.OpenProjectDialog;

public class ProjectOpenedEvent extends ComponentEvent<OpenProjectDialog> {

    private static final long serialVersionUID = -319905063093948474L;

    public ProjectOpenedEvent(OpenProjectDialog source, boolean fromClient) {
        super(source, fromClient);
    }
}
