package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents.DefaultVerbComponent;

public class AddAndOrVerbComponentEvent extends ComponentEvent<DefaultVerbComponent> {

    private static final long serialVersionUID = 1L;

    public AddAndOrVerbComponentEvent(DefaultVerbComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
