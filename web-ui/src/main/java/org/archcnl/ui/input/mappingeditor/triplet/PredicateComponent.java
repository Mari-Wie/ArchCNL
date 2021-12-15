package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.Relation;
import org.archcnl.ui.input.mappingeditor.events.PredicateSelectedEvent;
import org.archcnl.ui.input.mappingeditor.events.RelationListUpdateRequestedEvent;

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
        setValue(predicate.getName());
    }

    public void handleDropEvent(Object data) {
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

    private void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
