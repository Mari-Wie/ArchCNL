package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;

public class VariableSelectionComponent extends SelectionComponent {

    private static final long serialVersionUID = 8887336725233930402L;

    public VariableSelectionComponent() {
        super("Variable");
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
        addFocusListener(e -> fireEvent(new VariableListUpdateRequestedEvent(this, true)));
    }

    @Override
    protected void handleDropEvent(Object data) {
        // TODO: find a way to only handle events where the type is Hierarchynode to get rid of the
        // instanceof
        String droppedName = "";
        if (data instanceof Variable) {
            droppedName = ((Variable) data).getName();
        }
        checkedSetValue(droppedName);
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

    public void hightlightConflictingVariables(List<Variable> conflictingVariables) {
        if (!isEmpty()) {
            String value = getValue();
            if (conflictingVariables.stream().anyMatch(v -> v.getName().equals(value))) {
                // TODO only show a warning as there are cases where false conflicts are detected
                // see the limitation described in VariableManagerTest
                showErrorMessage("Possible type conflict");
            } else {
                setInvalid(false);
            }
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
