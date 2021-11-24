package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import java.util.Optional;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.View;

public class PredicateView extends ComboBox<String>
        implements PredicateContract.View, DropTarget<PredicateView> {

    private static final long serialVersionUID = -5423813782732362932L;
    private Presenter<View> presenter;

    public PredicateView(PredicateContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        setActive(true);
        setPlaceholder("Relation");
        updateItems();
        setClearButtonVisible(true);

        addValueChangeListener(
                event -> {
                    presenter.valueHasChanged();
                    setInvalid(false);
                });
        addDropListener(event -> event.getDragData().ifPresent(presenter::handleDropEvent));
    }

    @Override
    public void updateItems() {
        setItems(presenter.getRelationNames());
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

    @Override
    public void setItem(String relationName) {
        setValue(relationName);
    }
}
