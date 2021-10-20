package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.model.mappings.BooleanValue;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.StringValue;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.Presenter;
import org.archcnl.ui.input.mappingeditor.MappingEditorContract.View;
import org.archcnl.ui.input.mappingeditor.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class VariableStringBoolSelectionView extends HorizontalLayout {

    private static final long serialVersionUID = 8635404097691880603L;
    private BooleanSelectionView booleanSelectionView;
    private StringSelectionView stringSelectionView;
    private VariableSelectionView variableSelectionView;
    private VariableSelectionPresenter variableSelectionPresenter;
    private VariableManager variableManager;

    public VariableStringBoolSelectionView(
            VariableManager variableManager,
            boolean stringsAllowed,
            boolean booleanAllowed,
            Optional<Presenter<View>> optional) {
        this.variableManager = variableManager;
        setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        ComboBox<String> typeSelection = new ComboBox<>();
        List<String> items = new LinkedList<>();
        items.add("Variable");
        if (stringsAllowed) {
            items.add("String");
        }
        if (booleanAllowed) {
            items.add("Boolean");
        }
        typeSelection.setItems(items);
        typeSelection.setValue("Variable");
        if (items.size() > 1) {
            add(typeSelection);
        }
        showVariableSelectionView();

        typeSelection.addValueChangeListener(
                event -> {
                    String value = event.getValue();
                    if (value != null) {
                        switch (value) {
                            case "Variable":
                                showVariableSelectionView();
                                break;
                            case "String":
                                showStringSelectionView();
                                break;
                            case "Boolean":
                                showBooleanSelectionView();
                                break;
                            default:
                                // should never happen
                        }
                        if (optional.isPresent()) {
                            optional.get().selectedObjectTypeHasChanged();
                        }
                    }
                });
    }

    public ObjectType getObject()
            throws InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        ObjectType object;
        if (booleanSelectionView != null) {
            object = booleanSelectionView.getObject();
        } else if (stringSelectionView != null) {
            object = stringSelectionView.getObject();
        } else if (variableSelectionView != null && variableSelectionPresenter != null) {
            object = variableSelectionPresenter.getSelectedVariable();
        } else {
            // should never happen
            throw new RuntimeException("VariableStringBoolSelectionView implementation is faulty.");
        }
        return object;
    }

    public void setObject(ObjectType object) {
        if (object instanceof StringValue) {
            showStringSelectionView();
            stringSelectionView.setValue(((StringValue) object).getValue());
        } else if (object instanceof BooleanValue) {
            showBooleanSelectionView();
            booleanSelectionView.setValue(String.valueOf(((BooleanValue) object).getValue()));
        } else if (object instanceof Variable) {
            showVariableSelectionView();
            variableSelectionPresenter.setSelectedVariable((Variable) object);
        } else {
            // should never happen
            throw new RuntimeException("VariableStringBoolSelectionView implementation is faulty.");
        }
    }

    public void showErrorMessage(String message) {
        variableSelectionView.showErrorMessage(message);
    }

    private void removeAllSecondaryViews() {
        if (booleanSelectionView != null) {
            remove(booleanSelectionView);
        }
        if (stringSelectionView != null) {
            remove(stringSelectionView);
        }
        if (variableSelectionView != null) {
            remove(variableSelectionView);
        }
        booleanSelectionView = null;
        stringSelectionView = null;
        variableSelectionView = null;
        variableSelectionPresenter = null;
    }

    private void showStringSelectionView() {
        removeAllSecondaryViews();
        stringSelectionView = new StringSelectionView();
        add(stringSelectionView);
    }

    private void showBooleanSelectionView() {
        removeAllSecondaryViews();
        booleanSelectionView = new BooleanSelectionView();
        add(booleanSelectionView);
    }

    private void showVariableSelectionView() {
        removeAllSecondaryViews();
        variableSelectionPresenter = new VariableSelectionPresenter(variableManager);
        variableSelectionView = new VariableSelectionView(variableSelectionPresenter);
        add(variableSelectionView);
    }

    public ObjectType getSelectedObjectType() {
        ObjectType object;
        if (booleanSelectionView != null) {
            object = new BooleanValue(false);
        } else if (stringSelectionView != null) {
            object = new StringValue("");
        } else if (variableSelectionView != null && variableSelectionPresenter != null) {
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
}
