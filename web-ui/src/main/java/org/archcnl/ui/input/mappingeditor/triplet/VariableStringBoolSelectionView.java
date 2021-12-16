package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.common.BooleanValue;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.StringValue;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.input.mappingeditor.events.VariableCreationRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableFilterChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class VariableStringBoolSelectionView extends HorizontalLayout {

    private static final String STRING = "String";
    private static final String BOOLEAN = "Boolean";
    private static final String VARIABLE = "Variable";
    private static final long serialVersionUID = 8635404097691880603L;

    private BooleanSelectionComponent booleanSelectionComponent;
    private StringSelectionComponent stringSelectionComponent;
    private VariableSelectionComponent variableSelectionComponent;
    private ComboBox<String> typeSelection;

    public VariableStringBoolSelectionView(boolean stringsAllowed, boolean booleanAllowed) {
        setDefaultVerticalComponentAlignment(Alignment.END);

        typeSelection = new ComboBox<>();
        List<String> items = new LinkedList<>();
        items.add(VARIABLE);
        if (stringsAllowed) {
            items.add(STRING);
        }
        if (booleanAllowed) {
            items.add(BOOLEAN);
        }
        typeSelection.setItems(items);
        typeSelection.setValue(VARIABLE);
        if (items.size() > 1) {
            add(typeSelection);
        }
        showVariableSelectionView();

        typeSelection.addValueChangeListener(
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
    }

    public ObjectType getObject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        ObjectType object;
        if (booleanSelectionComponent != null) {
            object = booleanSelectionComponent.getObject();
        } else if (stringSelectionComponent != null) {
            object = stringSelectionComponent.getObject();
        } else if (variableSelectionComponent != null) {
            object = new Variable(variableSelectionComponent.getValue());
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
        add(stringSelectionComponent);
    }

    private void showBooleanSelectionView() {
        removeAllSecondaryViews();
        booleanSelectionComponent = new BooleanSelectionComponent();
        add(booleanSelectionComponent);
    }

    private void showVariableSelectionView() {
        removeAllSecondaryViews();
        variableSelectionComponent = new VariableSelectionComponent();
        variableSelectionComponent.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(
                VariableCreationRequestedEvent.class, this::fireEvent);
        variableSelectionComponent.addListener(
                VariableListUpdateRequestedEvent.class, this::fireEvent);
        add(variableSelectionComponent);
    }

    public ObjectType getSelectedObjectType() {
        ObjectType object;
        if (booleanSelectionComponent != null) {
            object = new BooleanValue(false);
        } else if (stringSelectionComponent != null) {
            object = new StringValue("");
        } else if (variableSelectionComponent != null) {
            try {
                object = new Variable("placeholder");
            } catch (InvalidVariableNameException e) {
                throw new RuntimeException(e.getMessage());
            }
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
