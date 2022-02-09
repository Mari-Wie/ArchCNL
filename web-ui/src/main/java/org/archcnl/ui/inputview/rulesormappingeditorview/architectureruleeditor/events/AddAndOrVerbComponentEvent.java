package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents.EveryOnlyNoVerbComponent;

import com.vaadin.flow.component.ComponentEvent;

public class AddAndOrVerbComponentEvent extends ComponentEvent<EveryOnlyNoVerbComponent> {

    private static final long serialVersionUID = 1L;

    public AddAndOrVerbComponentEvent(EveryOnlyNoVerbComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
