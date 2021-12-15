package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionComponent;

public class VariableListUpdateRequestedEvent extends ComponentEvent<VariableSelectionComponent> {

    private static final long serialVersionUID = -4258030927652688024L;

    public VariableListUpdateRequestedEvent(VariableSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
