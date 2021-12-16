package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.input.mappingeditor.events.VariableCreationRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableFilterChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class VariableSelectionComponent extends ComboBox<String>
        implements DropTarget<VariableSelectionComponent> {

    private static final long serialVersionUID = 8887336725233930402L;
    private static final String CREATE_ITEM = "Create new variable ";
    private static final String CREATE_ITEM_PATTERN = CREATE_ITEM + "\"\\w+\"";

    public VariableSelectionComponent() {
        setActive(true);
        setPlaceholder("Variable");
        setClearButtonVisible(true);
        setPattern("\\w+");
        setPreventInvalidInput(true);

        addListeners();
    }

    private void addListeners() {
        addFilterChangeListener(
                event ->
                        fireEvent(
                                new VariableFilterChangedEvent(
                                        this, true, event.getFilter(), CREATE_ITEM)));
        addCustomValueSetListener(
                event -> {
                    setInvalid(false);
                    fireEvent(new VariableCreationRequestedEvent(this, true, event.getDetail()));
                });
        addValueChangeListener(
                event -> {
                    setInvalid(false);
                    if (event.getValue() != null && event.getValue().matches(CREATE_ITEM_PATTERN)) {
                        fireEvent(
                                new VariableCreationRequestedEvent(this, true, getFilterString()));
                    }
                });
        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
        addFocusListener(e -> fireEvent(new VariableListUpdateRequestedEvent(this, true)));
    }

    public void setVariable(Variable variable) {
        setValue(variable.getName());
    }

    public Variable getVariable()
            throws SubjectOrObjectNotDefinedException, InvalidVariableNameException {
        String variableName =
                getOptionalValue().orElseThrow(SubjectOrObjectNotDefinedException::new);
        return new Variable(variableName);
    }

    public void highlightWhenEmpty() {
        if (getOptionalValue().isEmpty()) {
            showErrorMessage("Variable not set");
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
