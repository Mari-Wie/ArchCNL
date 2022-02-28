package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;

public class VariableSelectionComponent extends ComboBox<String>
        implements DropTarget<VariableSelectionComponent> {

    private static final long serialVersionUID = 8887336725233930402L;

    public VariableSelectionComponent() {
        setActive(true);
        setPlaceholder("Variable");
        setClearButtonVisible(true);
        setPattern("\\w+");
        setPreventInvalidInput(true);

        addListeners();
    }

    private void addListeners() {
        addCustomValueSetListener(
                event -> {
                    setInvalid(false);
                    fireEvent(new VariableCreationRequestedEvent(this, true, event.getDetail()));
                });
        addValueChangeListener(
                event -> {
                    setInvalid(false);
                    fireEvent(new VariableSelectedEvent(this, true));
                });
        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
        addFocusListener(e -> fireEvent(new VariableListUpdateRequestedEvent(this, true)));
    }

    public void setVariable(Variable variable) {
        try {
            setValue(variable.getName());
        } catch (IllegalStateException e) {
            fireEvent(new VariableCreationRequestedEvent(this, false, variable.getName()));
        }
    }

    public Variable getVariable() throws SubjectOrObjectNotDefinedException {
        String variableName =
                getOptionalValue().orElseThrow(SubjectOrObjectNotDefinedException::new);
        return new Variable(variableName);
    }

    public void highlightWhenEmpty() {
        if (isEmpty()) {
            showErrorMessage("Variable not set");
        }
    }

    public void hightlightConflictingVariables(List<Variable> conflictingVariables) {
        if (!isEmpty()) {
            String value = getValue();
            if (conflictingVariables.stream().anyMatch(v -> v.getName().equals(value))) {
                showErrorMessage("Possible type conflict");
            }
        }
    }

    private void handleDropEvent(Object data) {
        if (data instanceof Variable) {
            Variable variable = (Variable) data;
            setValue(variable.getName());
        } else {
            showErrorMessage("Not a Variable");
        }
    }

    public void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
