package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;

public class VariableListUpdateRequestedEvent extends ComponentEvent<VariableSelectionComponent> {

    private static final long serialVersionUID = -4258030927652688024L;

    public VariableListUpdateRequestedEvent(VariableSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(VariableManager variableManager) {
        String currentItem = getSource().getValue();
        getSource().setItems(variableManager.getVariables().stream().map(Variable::getName));
        getSource().setValue(currentItem);
    }
}
