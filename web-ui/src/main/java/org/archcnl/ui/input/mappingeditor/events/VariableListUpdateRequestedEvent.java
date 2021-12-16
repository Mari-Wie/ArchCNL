package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionComponent;

public class VariableListUpdateRequestedEvent extends ComponentEvent<VariableSelectionComponent> {

    private static final long serialVersionUID = -4258030927652688024L;

    public VariableListUpdateRequestedEvent(VariableSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(VariableManager variableManager) {
        getSource().setItems(variableManager.getVariables().stream().map(Variable::getName));
    }
}
