package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;

public class PredicateComponent extends ComboBox<String> implements DropTarget<PredicateComponent> {

    private static final long serialVersionUID = -5423813782732362932L;

    public PredicateComponent() {
        setActive(true);
        setPlaceholder("Relation");
        setClearButtonVisible(true);

        addAttachListener(e -> fireEvent(new RelationListUpdateRequestedEvent(this, true)));
        addValueChangeListener(
                e -> {
                    setInvalid(false);
                    fireEvent(new PredicateSelectedEvent(this, true));
                });
        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
    }

    public Optional<String> getSelectedItem() {
        return getOptionalValue();
    }

    public void setPredicate(Relation predicate) {
        try {
            setValue(predicate.getName());
        } catch (IllegalStateException e) {
            fireEvent(new RelationListUpdateRequestedEvent(this, false));
            setValue(predicate.getName());
        }
    }

    private void handleDropEvent(Object data) {
        if (data instanceof Relation) {
            Relation relation = (Relation) data;
            setValue(relation.getName());
        } else {
            showErrorMessage("Not a Relation");
        }
    }

    public void highlightWhenEmpty() {
        if (getSelectedItem().isEmpty()) {
            showErrorMessage("Predicate not set");
        }
    }

    public void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
