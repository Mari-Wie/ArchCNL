package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.MainView;

public class ProjectOptionRequestedEvent extends ComponentEvent<MainView> {

    public enum ProjectOption {
        NEW,
        OPEN,
        SAVE,
        SAVE_AS
    };

    private static final long serialVersionUID = 8536361781976666283L;
    private ProjectOption option;

    public ProjectOptionRequestedEvent(
            final MainView source, final boolean fromClient, final ProjectOption option) {
        super(source, fromClient);
        this.option = option;
    }

    public ProjectOption getOption() {
        return option;
    }
}
