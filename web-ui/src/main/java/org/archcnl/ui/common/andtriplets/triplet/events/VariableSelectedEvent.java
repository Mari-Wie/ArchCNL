package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;

public class VariableSelectedEvent extends ComponentEvent<VariableSelectionComponent> {

    private static final long serialVersionUID = -1969439375456440034L;

    public VariableSelectedEvent(VariableSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
