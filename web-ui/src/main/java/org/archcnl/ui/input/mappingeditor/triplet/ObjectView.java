package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import java.util.Optional;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.View;

public class ObjectView extends ComboBox<String> implements ObjectContract.View {

    private static final long serialVersionUID = -1105253743414019620L;
    private Presenter<View> presenter;

    public ObjectView(ObjectContract.Presenter<View> presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        setLabel("Object");
        setPlaceholder("Concept");
        updateItems();
        setClearButtonVisible(true);

        addValueChangeListener(
                event -> {
                    // Check if predicate can relate to this concept
                    setInvalid(false);
                });
    }

    @Override
    public void updateItems() {
        setItems(presenter.getConceptNames());
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
