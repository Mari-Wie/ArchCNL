package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.InvalidVariableNameException;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;

public class VariableFilterChangedEvent extends ComponentEvent<VariableSelectionComponent> {

    private static final long serialVersionUID = 1209710165251122316L;
    private final String filterString;
    private final String createItemText;

    public VariableFilterChangedEvent(
            VariableSelectionComponent source,
            boolean fromClient,
            String filterString,
            String createItemText) {
        super(source, fromClient);
        this.filterString = filterString;
        this.createItemText = createItemText;
    }

    public void handleEvent(VariableManager variableManager) {
        List<String> items =
                variableManager.getVariables().stream()
                        .map(Variable::getName)
                        .collect(Collectors.toList());
        if (!filterString.isBlank() && !doesVariableExist(filterString, variableManager)) {
            String createItem = createItemText + "\"" + filterString + "\"";
            items.add(0, createItem);
        }
        String value = getSource().getValue();
        getSource().setItems(items);
        getSource().setValue(value);
    }

    private boolean doesVariableExist(String variableName, VariableManager variableManager) {
        try {
            return variableManager.doesVariableExist(new Variable(variableName));
        } catch (InvalidVariableNameException e) {
            return false;
        }
    }
}
