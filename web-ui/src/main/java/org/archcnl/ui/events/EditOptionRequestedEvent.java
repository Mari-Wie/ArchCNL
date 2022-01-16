package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.ui.MainView;

public class EditOptionRequestedEvent extends ComponentEvent<MainView> {

    public enum EditOption {
        UNDO,
        REDO
    }

    private static final Logger LOG = LogManager.getLogger(EditOptionRequestedEvent.class);
    private static final long serialVersionUID = 487783076143039066L;
    private EditOption option;

    public EditOptionRequestedEvent(
            final MainView source, final boolean fromClient, final EditOption option) {
        super(source, fromClient);
        this.option = option;
    }

    public void handleEvent() {
        switch (option) {
            case REDO:
                EditOptionRequestedEvent.LOG.warn("REDO is not implemented");
                break;
            case UNDO:
                EditOptionRequestedEvent.LOG.warn("UNDO is not implemented");
                break;
            default:
                EditOptionRequestedEvent.LOG.warn(
                        "Unhandled EditOption {} appeared in EditOptionRequestedEvent.", option);
                break;
        }
    }
}
