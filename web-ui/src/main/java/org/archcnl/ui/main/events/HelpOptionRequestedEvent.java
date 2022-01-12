package org.archcnl.ui.main.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.main.MainView;

public class HelpOptionRequestedEvent extends ComponentEvent<MainView> {

    private static final long serialVersionUID = -7786830912543124006L;

    public HelpOptionRequestedEvent(MainView source, boolean fromClient) {
        super(source, fromClient);
    }
}
