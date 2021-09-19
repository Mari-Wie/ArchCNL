package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import java.util.List;
import java.util.Optional;
import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionContract.View;

public class VariableSelectionView extends ComboBox<String>
        implements VariableSelectionContract.View, DropTarget<VariableSelectionView> {

    private static final long serialVersionUID = 8887336725233930402L;
    private Presenter<View> presenter;
    private static final String CREATE_ITEM = "Create new variable ";
    private static final String CREATE_ITEM_PATTERN = CREATE_ITEM + "\"\\w+\"";

    public VariableSelectionView(VariableSelectionContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        setActive(true);
        setLabel("Object");
        setPlaceholder("Variable");
        updateItems();
        setClearButtonVisible(true);
        setPattern("\\w+");
        setPreventInvalidInput(true);

        addFilterChangeListener(event -> addCreateItem(event.getFilter()));
        addCustomValueSetListener(
                event -> {
                    if (!presenter.doesVariableExist(event.getDetail())) {
                        presenter.addCustomValue(event.getDetail());
                    }
                });
        addValueChangeListener(
                event -> {
                    if (event.getValue() != null && event.getValue().matches(CREATE_ITEM_PATTERN)) {
                        presenter.addCustomValue(event.getOldValue());
                    }
                    setInvalid(false);
                });
        addDropListener(event -> event.getDragData().ifPresent(presenter::handleDropEvent));
    }

    private void addCreateItem(String currentFilter) {
        List<String> items = presenter.getVariableNames();
        if (!currentFilter.isBlank() && !presenter.doesVariableExist(currentFilter)) {
            String createItem = CREATE_ITEM + "\"" + currentFilter + "\"";
            items.add(0, createItem);
        }
        setItems(items);
    }

    @Override
    public void updateItems() {
        setItems(presenter.getVariableNames());
    }

    @Override
    public void setItem(String variableName) {
        setValue(variableName);
    }

    @Override
    public void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    @Override
    public Optional<String> getSelectedItem() {
        return Optional.ofNullable(getValue());
    }
}
