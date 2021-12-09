package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableSelectedEvent;

public class VariableSelectionComponent extends ComboBox<String>
        implements DropTarget<VariableSelectionComponent> {

    private static final long serialVersionUID = 8887336725233930402L;
    private static final String CREATE_ITEM = "Create new variable ";
    private static final String CREATE_ITEM_PATTERN = CREATE_ITEM + "\"\\w+\"";

    public VariableSelectionComponent(VariableManager variableManager) {
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
                    fireEvent(new VariableSelectedEvent(this, false));
                });
        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
        addAttachListener(e -> update());
    }

    public void update() {
        fireEvent(new VariableListUpdateRequestedEvent(this, false));
    }

    public void setVariable(Variable variable) {
        setValue(variable.getName());
    }

    public void handleDropEvent(Object data) {
        if (data instanceof Variable) {
            Variable variable = (Variable) data;
            setValue(variable.getName());
        } else {
            showErrorMessage("Not a Variable");
        }
    }

    protected void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
