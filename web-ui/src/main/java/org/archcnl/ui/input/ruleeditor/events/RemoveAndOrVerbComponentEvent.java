package org.archcnl.ui.input.ruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.ruleeditor.components.verbcomponents.EveryOnlyNoVerbComponent;

public class RemoveAndOrVerbComponentEvent extends ComponentEvent<EveryOnlyNoVerbComponent> {

    private static final long serialVersionUID = 1L;

    public RemoveAndOrVerbComponentEvent(EveryOnlyNoVerbComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
