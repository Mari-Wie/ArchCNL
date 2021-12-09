package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class VariableSelectionComponent extends ComboBox<String>
        implements DropTarget<VariableSelectionComponent>, PropertyChangeListener {

    private static final long serialVersionUID = 8887336725233930402L;
    private static final String CREATE_ITEM = "Create new variable ";
    private static final String CREATE_ITEM_PATTERN = CREATE_ITEM + "\"\\w+\"";
    private VariableManager variableManager;

    public VariableSelectionComponent(VariableManager variableManager) {
        this.variableManager = variableManager;
        this.variableManager.addPropertyChangeListener(this);
        setActive(true);
        setPlaceholder("Variable");
        updateItems();
        setClearButtonVisible(true);
        setPattern("\\w+");
        setPreventInvalidInput(true);

        addFilterChangeListener(event -> addCreateItem(event.getFilter()));
        addCustomValueSetListener(
                event -> {
                    setInvalid(false);
                    if (!doesVariableExist(event.getDetail())) {
                        addCustomValue(event.getDetail());
                    }
                });
        addValueChangeListener(
                event -> {
                    if (event.getValue() != null && event.getValue().matches(CREATE_ITEM_PATTERN)) {
                        addCustomValue(event.getOldValue());
                    }
                    setInvalid(false);
                });
        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
    }

    public void updateItems() {
        String value = getValue();
        setItems(getVariableNames());
        setValue(value);
    }

    public void setVariable(Variable variable) {
        if (!variableManager.doesVariableExist(variable)) {
            try {
                variableManager.addVariable(variable);
                updateItems();
            } catch (VariableAlreadyExistsException e) {
                // cannot occur
                e.printStackTrace();
            }
        }
        setValue(variable.getName());
    }

    public Variable getVariable()
            throws SubjectOrObjectNotDefinedException, InvalidVariableNameException {
        String variableName =
                getSelectedItem().orElseThrow(SubjectOrObjectNotDefinedException::new);
        return new Variable(variableName);
    }

    private void addCustomValue(String variableName) {
        try {
            Variable newVariable = new Variable(variableName);
            variableManager.addVariable(newVariable);
            updateItems();
            setValue(newVariable.getName());
        } catch (InvalidVariableNameException e) {
            showErrorMessage("Invalid variable name");
        } catch (VariableAlreadyExistsException e) {
            showErrorMessage("Variable already exists");
        }
    }

    public void handleDropEvent(Object data) {
        if (data instanceof Variable) {
            Variable variable = (Variable) data;
            setValue(variable.getName());
        } else {
            showErrorMessage("Not a Variable");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateItems();
    }

    private void addCreateItem(String currentFilter) {
        List<String> items = getVariableNames();
        if (!currentFilter.isBlank() && !doesVariableExist(currentFilter)) {
            String createItem = CREATE_ITEM + "\"" + currentFilter + "\"";
            items.add(0, createItem);
        }
        String value = getValue();
        setItems(items);
        setValue(value);
    }

    protected void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    private Optional<String> getSelectedItem() {
        return Optional.ofNullable(getValue());
    }

    private List<String> getVariableNames() {
        return variableManager.getVariables().stream()
                .map(Variable::getName)
                .collect(Collectors.toList());
    }

    private boolean doesVariableExist(String variableName) {
        try {
            return variableManager.doesVariableExist(new Variable(variableName));
        } catch (InvalidVariableNameException e) {
            return false;
        }
    }
}
