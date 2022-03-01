package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents.DefaultVerbComponent;

public class RemoveAndOrVerbComponentEvent extends ComponentEvent<DefaultVerbComponent> {

    private static final long serialVersionUID = 1L;

    public RemoveAndOrVerbComponentEvent(DefaultVerbComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
