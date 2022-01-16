package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.MainView;

public class HelpOptionRequestedEvent extends ComponentEvent<MainView> {

    private static final long serialVersionUID = -7786830912543124006L;

    public HelpOptionRequestedEvent(final MainView source, final boolean fromClient) {
        super(source, fromClient);
    }
}
