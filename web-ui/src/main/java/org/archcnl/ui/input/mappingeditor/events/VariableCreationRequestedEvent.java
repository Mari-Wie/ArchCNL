package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionComponent;

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
