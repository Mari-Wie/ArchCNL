package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents.EveryOnlyNoVerbComponent;

public class AddAndOrVerbComponentEvent extends ComponentEvent<EveryOnlyNoVerbComponent> {

    private static final long serialVersionUID = 1L;

    public AddAndOrVerbComponentEvent(EveryOnlyNoVerbComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
