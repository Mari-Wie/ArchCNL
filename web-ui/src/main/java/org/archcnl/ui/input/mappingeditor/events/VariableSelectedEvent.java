package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionComponent;

public class VariableSelectedEvent extends ComponentEvent<VariableSelectionComponent> {

    private static final long serialVersionUID = -1653546781366369196L;

    public VariableSelectedEvent(VariableSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
