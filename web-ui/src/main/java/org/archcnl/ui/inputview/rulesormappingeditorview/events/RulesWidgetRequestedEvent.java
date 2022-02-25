package org.archcnl.ui.inputview.rulesormappingeditorview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class RulesWidgetRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -6772599571697021016L;

    public RulesWidgetRequestedEvent(final Component source, final boolean fromClient) {
        super(source, fromClient);
    }
}
