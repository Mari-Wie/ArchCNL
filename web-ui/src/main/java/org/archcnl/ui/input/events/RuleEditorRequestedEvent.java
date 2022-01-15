package org.archcnl.ui.input.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class RuleEditorRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -6772599571697021016L;

    public RuleEditorRequestedEvent(Component source, boolean fromClient) {
        super(source, fromClient);
    }
}
