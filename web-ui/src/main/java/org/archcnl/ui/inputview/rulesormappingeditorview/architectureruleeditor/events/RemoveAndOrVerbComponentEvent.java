package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents.DefaultStatementComponent;

public class RemoveAndOrVerbComponentEvent extends ComponentEvent<DefaultStatementComponent> {

    private static final long serialVersionUID = 1L;

    public RemoveAndOrVerbComponentEvent(DefaultStatementComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
