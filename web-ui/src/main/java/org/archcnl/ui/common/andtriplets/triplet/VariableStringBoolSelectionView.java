package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;

public class VariableStringBoolSelectionView extends HorizontalLayout {

    private static final String STRING = "String";
    private static final String BOOLEAN = "Boolean";
    private static final String VARIABLE = "Variable";
    private static final long serialVersionUID = 8635404097691880603L;

    private BooleanSelectionComponent booleanSelectionComponent;
    private StringSelectionComponent stringSelectionComponent;
    private VariableSelectionComponent variableSelectionComponent;
    private ComboBox<String> typeSelection;
    private Optional<String> label = Optional.empty();

    public VariableStringBoolSelectionView() {
        setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        updateTypeSelectionItems(true, true);
        showVariableSelectionView();
    }

    public void updateTypeSelectionItems(boolean stringsAllowed, boolean booleanAllowed) {
        String oldValue = null;
        if (typeSelection != null) {
            remove(typeSelection);
            oldValue = typeSelection.getValue();
        }
        ComboBox<String> newTypeSelection = new ComboBox<>();
        List<String> items = new LinkedList<>();
        items.add(VARIABLE);
        if (stringsAllowed) {
            items.add(STRING);
        }
        if (booleanAllowed) {
            items.add(BOOLEAN);
        }
        newTypeSelection.setItems(items);
        if (items.contains(oldValue)) {
            newTypeSelection.setValue(oldValue);
        } else {
            newTypeSelection.setValue(VARIABLE);
        }

        newTypeSelection.addValueChangeListener(
                event -> {
                    String value = event.getValue();
                    if (value != null) {
                        switch (value) {
                            case VARIABLE:
                                showVariableSelectionView();
                                break;
                            case STRING:
                                showStringSelectionView();
                                break;
                            case BOOLEAN:
                                showBooleanSelectionView();
                                break;
                            default:
                                // should never happen
                        }
                    }
                });
        typeSelection = newTypeSelection;
        if (items.size() > 1) {
            addComponentAsFirst(typeSelection);
        }
    }

    public ObjectType getObject() throws SubjectOrObjectNotDefinedException {
        ObjectType object;
        if (booleanSelectionComponent != null) {
            object = booleanSelectionComponent.getObject();
        } else if (stringSelectionComponent != null) {
            object = stringSelectionComponent.getObject();
        } else if (variableSelectionComponent != null) {
            object = variableSelectionComponent.getVariable();
        } else {
            // should never happen
            throw new RuntimeException("VariableStringBoolSelectionView implementation is faulty.");
        }
        return object;
    }

    public void setObject(ObjectType object) {
        if (object instanceof StringValue) {
            typeSelection.setValue(STRING);
            showStringSelectionView();
            stringSelectionComponent.setValue(((StringValue) object).getValue());
        } else if (object instanceof BooleanValue) {
            typeSelection.setValue(BOOLEAN);
            showBooleanSelectionView();
            booleanSelectionComponent.setValue(String.valueOf(((BooleanValue) object).getValue()));
        } else if (object instanceof Variable) {
            typeSelection.setValue(VARIABLE);
            showVariableSelectionView();
            variableSelectionComponent.setVariable((Variable) object);
        } else {
            // should never happen
            throw new RuntimeException("VariableStringBoolSelectionView implementation is faulty.");
        }
    }

    public void showErrorMessage(String message) {
        variableSelectionComponent.showErrorMessage(message);
    }

    public void setLabel(String text) {
        label = Optional.of(text);
        // update currently shown view
        if (booleanSelectionComponent != null) {
            booleanSelectionComponent.setLabel(text);
        }
        if (stringSelectionComponent != null) {
            stringSelectionComponent.setLabel(text);
        }
        if (variableSelectionComponent != null) {
            variableSelectionComponent.setLabel(text);
        }
    }

    public void hightlightConflictingVariables(List<Variable> conflictingVariables) {
        if (variableSelectionComponent != null) {
            variableSelectionComponent.hightlightConflictingVariables(conflictingVariables);
        }
    }

    private void removeAllSecondaryViews() {
        if (booleanSelectionComponent != null) {
            remove(booleanSelectionComponent);
        }
        if (stringSelectionComponent != null) {
            remove(stringSelectionComponent);
        }
        if (variableSelectionComponent != null) {
            remove(variableSelectionComponent);
        }
        booleanSelectionComponent = null;
        stringSelectionComponent = null;
        variableSelectionComponent = null;
    }

    private void showStringSelectionView() {
        removeAllSecondaryViews();
        stringSelectionComponent = new StringSelectionComponent();
        if (label.isPresent()) {
            stringSelectionComponent.setLabel(label.get());
        }
        add(stringSelectionComponent);
    }

    private void showBooleanSelectionView() {
        removeAllSecondaryViews();
        booleanSelectionComponent = new BooleanSelectionComponent();
        if (label.isPresent()) {
            booleanSelectionComponent.setLabel(label.get());
        }
        add(booleanSelectionComponent);
    }

    private void showVariableSelectionView() {
        removeAllSecondaryViews();
        variableSelectionComponent = new VariableSelectionComponent();
        if (label.isPresent()) {
            variableSelectionComponent.setLabel(label.get());
        }
        variableSelectionComponent.addListener(
                VariableCreationRequestedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(
                VariableListUpdateRequestedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(VariableSelectedEvent.class, this::fireEvent);
        add(variableSelectionComponent);
    }

    public ObjectType getSelectedObjectType() {
        ObjectType object;
        if (booleanSelectionComponent != null) {
            object = new BooleanValue(false);
        } else if (stringSelectionComponent != null) {
            object = new StringValue("");
        } else if (variableSelectionComponent != null) {
            object = new Variable("placeholder");

        } else {
            // should never happen
            throw new RuntimeException("VariableStringBoolSelectionView implementation is faulty.");
        }
        return object;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
