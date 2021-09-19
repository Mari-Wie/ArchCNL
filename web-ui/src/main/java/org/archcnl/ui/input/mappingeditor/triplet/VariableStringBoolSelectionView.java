package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectNotDefinedException;

public class VariableStringBoolSelectionView extends HorizontalLayout {

    private static final long serialVersionUID = 8635404097691880603L;
    private BooleanSelectionView booleanSelectionView;
    private StringSelectionView stringSelectionView;
    private VariableSelectionView variableSelectionView;
    private VariableSelectionPresenter variableSelectionPresenter;
    private VariableManager variableManager;

    public VariableStringBoolSelectionView(
            VariableManager variableManager, boolean stringsAllowed, boolean booleanAllowed) {
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
                    }
                });
    }

    public ObjectType getObject() throws VariableDoesNotExistException, SubjectNotDefinedException {
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
}
