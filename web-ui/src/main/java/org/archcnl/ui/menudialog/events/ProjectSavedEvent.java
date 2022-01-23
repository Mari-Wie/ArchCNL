package org.archcnl.ui.menudialog.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.menudialog.SaveProjectDialog;

public class ProjectSavedEvent extends ComponentEvent<SaveProjectDialog> {

    private static final long serialVersionUID = -843578817293474525L;

    public ProjectSavedEvent(SaveProjectDialog source, boolean fromClient) {
        super(source, fromClient);
    }
}
