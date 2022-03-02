package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;

public class ConceptSelectionComponent extends SelectionComponent {

    private static final long serialVersionUID = 5874026321476515429L;
    private Optional<Concept> selectedConcept = Optional.empty();

    public ConceptSelectionComponent() {
        super("Concept");
        addAttachListener(e -> fireEvent(new ConceptListUpdateRequestedEvent(this, true)));
        addValueChangeListener(
                event -> {
                    setInvalid(false);
                    fireEvent(new ConceptSelectedEvent(this, true));
                });
    }

    public Optional<String> getSelectedItem() {
        return getOptionalValue();
    }

    public void setObject(Concept concept) {
        try {
            setValue(concept.getName());
        } catch (IllegalStateException e) {
            fireEvent(new ConceptListUpdateRequestedEvent(this, false));
            setValue(concept.getName());
        } finally {
            selectedConcept = Optional.of(concept);
        }
    }

    public void setInternalConcept(Concept concept) {
        selectedConcept = Optional.of(concept);
    }

    public Optional<Concept> getConcept() {
        return selectedConcept;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
