package org.archcnl.ui.main.events;

import com.vaadin.flow.component.ComponentEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.ui.main.MainView;

public class EditOptionRequestedEvent extends ComponentEvent<MainView> {

    public enum EditOption {
        UNDO,
        REDO
    }

    private static final Logger LOG = LogManager.getLogger(EditOptionRequestedEvent.class);
    private static final long serialVersionUID = 487783076143039066L;
    private EditOption option;

    public EditOptionRequestedEvent(MainView source, boolean fromClient, EditOption option) {
        super(source, fromClient);
        this.option = option;
    }

    public void handleEvent() {
        switch (option) {
            case REDO:
                LOG.warn("REDO is not implemented");
                break;
            case UNDO:
                LOG.warn("UNDO is not implemented");
                break;
            default:
                LOG.warn("Unhandled EditOption {} appeared in EditOptionRequestedEvent.", option);
                break;
        }
    }
}
