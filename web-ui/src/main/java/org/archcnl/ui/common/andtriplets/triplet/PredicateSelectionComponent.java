package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;

public class PredicateSelectionComponent extends SelectionComponent {

    private static final long serialVersionUID = -5423813782732362932L;
    private Optional<Relation> selectedRelation = Optional.empty();

    public PredicateSelectionComponent() {
        super("Relation");

        addAttachListener(e -> fireEvent(new RelationListUpdateRequestedEvent(this, true)));
        addValueChangeListener(
                e -> {
                    setInvalid(false);
                    fireEvent(new PredicateSelectedEvent(this, true));
                });
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
        } finally {
            selectedRelation = Optional.of(predicate);
        }
    }

    public void setInternalRelation(Relation relation) {
        selectedRelation = Optional.of(relation);
    }

    public Optional<Relation> getRelation() {
        return selectedRelation;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
