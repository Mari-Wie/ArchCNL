package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import java.util.Optional;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateContract.View;

public class PredicateView extends ComboBox<String> implements PredicateContract.View {

    private static final long serialVersionUID = -5423813782732362932L;
    private Presenter<View> presenter;

    public PredicateView(PredicateContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        setLabel("Predicate");
        setPlaceholder("Relation");
        updateItems();
        setClearButtonVisible(true);

        addValueChangeListener(
                event -> {
                    // Check is relation can relate to object
                    setInvalid(false);
                });
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
}
