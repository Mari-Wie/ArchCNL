package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents.EveryOnlyNoVerbComponent;

public class RemoveAndOrVerbComponentEvent extends ComponentEvent<EveryOnlyNoVerbComponent> {

    private static final long serialVersionUID = 1L;

    public RemoveAndOrVerbComponentEvent(EveryOnlyNoVerbComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
