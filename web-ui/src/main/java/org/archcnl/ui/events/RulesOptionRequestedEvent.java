package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.ui.MainView;

public class RulesOptionRequestedEvent extends ComponentEvent<MainView> {

    public enum RulesOption {
        IMPORT_FROM_FILE,
        IMPORT_RULE_PRESETS
    }

    private static final Logger LOG = LogManager.getLogger(RulesOptionRequestedEvent.class);
    private static final long serialVersionUID = 7994033413072875198L;
    private RulesOption option;

    public RulesOptionRequestedEvent(
            final MainView source, final boolean fromClient, final RulesOption option) {
        super(source, fromClient);
        this.option = option;
    }

    public RulesOption getOption() {
        return option;
    }
}
