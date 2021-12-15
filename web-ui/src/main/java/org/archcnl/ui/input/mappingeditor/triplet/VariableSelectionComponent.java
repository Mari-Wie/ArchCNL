package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.Variable;
import org.archcnl.ui.input.mappingeditor.events.VariableCreationRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableFilterChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;

public class VariableSelectionComponent extends ComboBox<String>
        implements DropTarget<VariableSelectionComponent> {

    private static final long serialVersionUID = 8887336725233930402L;
    private static final String CREATE_ITEM = "Create new variable ";
    private static final String CREATE_ITEM_PATTERN = CREATE_ITEM + "\"\\w+\"";

    public VariableSelectionComponent(String initialValue) {
        setActive(true);
        setPlaceholder("Variable");
        setClearButtonVisible(true);
        setPattern("\\w+");
        setPreventInvalidInput(true);

        addListeners();

        setValue(initialValue);
    }

    private void addListeners() {
        addFilterChangeListener(
                event -> fireEvent(new VariableFilterChangedEvent(this, true, event.getFilter())));
        addCustomValueSetListener(
                event -> {
                    setInvalid(false);
                    fireEvent(new VariableCreationRequestedEvent(this, true, event.getDetail()));
                });
        addValueChangeListener(
                event -> {
                    if (event.getValue() != null && event.getValue().matches(CREATE_ITEM_PATTERN)) {
                        fireEvent(
                                new VariableCreationRequestedEvent(this, true, getFilterString()));
                    }
                    setInvalid(false);
                });
        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
        addAttachListener(e -> fireEvent(new VariableListUpdateRequestedEvent(this, true)));
    }

    public Optional<String> getSelectedValue() {
        return getOptionalValue();
    }

    public void highlightWhenEmpty() {
        if (getSelectedValue().isEmpty()) {
            showErrorMessage("Variable not set");
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
