package org.archcnl.ui.main.events;

import com.vaadin.flow.component.ComponentEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.ui.main.MainView;

public class RulesOptionRequestedEvent extends ComponentEvent<MainView> {

    public enum RulesOption {
        IMPORT_FROM_FILE,
        IMPORT_RULE_PRESETS
    }

    private static final Logger LOG = LogManager.getLogger(RulesOptionRequestedEvent.class);
    private static final long serialVersionUID = 7994033413072875198L;
    private RulesOption option;

    public RulesOptionRequestedEvent(MainView source, boolean fromClient, RulesOption option) {
        super(source, fromClient);
        this.option = option;
    }

    public void handleEvent() {
        switch (option) {
            case IMPORT_FROM_FILE:
                LOG.warn("{} is not implemented", option);
                break;
            case IMPORT_RULE_PRESETS:
                LOG.warn("{} is not implemented", option);
                break;
            default:
                LOG.warn("Unhandled RulesOption {} appeared in RulesOptionRequestedEvent.", option);
                break;
        }
    }
}
