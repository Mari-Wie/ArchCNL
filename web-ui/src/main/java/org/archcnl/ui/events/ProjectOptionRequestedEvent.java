package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.input.ProjectManager;
import org.archcnl.ui.MainView;
import org.archcnl.ui.common.ConfirmDialog;
import org.archcnl.ui.menudialog.OpenProjectDialog;
import org.archcnl.ui.menudialog.SaveProjectDialog;

public class ProjectOptionRequestedEvent extends ComponentEvent<MainView> {

    public enum ProjectOption {
        NEW,
        OPEN,
        SAVE,
        SAVE_AS
    };

    private static final Logger LOG = LogManager.getLogger(ProjectOptionRequestedEvent.class);
    private static final long serialVersionUID = 8536361781976666283L;
    private ProjectOption option;

    public ProjectOptionRequestedEvent(
            final MainView source, final boolean fromClient, final ProjectOption option) {
        super(source, fromClient);
        this.option = option;
    }

    public void handleEvent() {
        switch (option) {
            case NEW:
                getSource().showNewTab();
                break;
            case OPEN:
                new OpenProjectDialog().open();
                break;
            case SAVE:
                try {
                    ProjectManager.getInstance().saveProject();
                } catch (final IOException e) {
                    new ConfirmDialog("Project file could not be written.").open();
                }
                break;
            case SAVE_AS:
                new SaveProjectDialog().open();
                break;
            default:
                ProjectOptionRequestedEvent.LOG.warn(
                        "Unhandled ProjectOption {} appeared in ProjectOptionRequestedEvent.",
                        option);
                break;
        }
    }
}
