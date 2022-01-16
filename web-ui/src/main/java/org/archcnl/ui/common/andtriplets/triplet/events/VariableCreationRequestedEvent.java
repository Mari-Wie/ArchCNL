package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;

public class VariableCreationRequestedEvent extends ComponentEvent<VariableSelectionComponent> {

    private static final long serialVersionUID = 6156713614013808930L;
    private final String variableName;

    public VariableCreationRequestedEvent(
            VariableSelectionComponent source, boolean fromClient, String variableName) {
        super(source, fromClient);
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}
