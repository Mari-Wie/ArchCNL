package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.Concept;
import org.archcnl.ui.input.mappingeditor.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.ConceptSelectedEvent;

public class ConceptSelectionComponent extends ComboBox<String>
        implements DropTarget<ConceptSelectionComponent> {

    private static final long serialVersionUID = 5874026321476515429L;

    public ConceptSelectionComponent() {
        setActive(true);
        setPlaceholder("Concept");
        setClearButtonVisible(true);

        addAttachListener(e -> fireEvent(new ConceptListUpdateRequestedEvent(this, true)));
        addValueChangeListener(
                event -> {
                    setInvalid(false);
                    fireEvent(new ConceptSelectedEvent(this, true));
                });
        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
    }

    public Optional<String> getSelectedItem() {
        return getOptionalValue();
    }

    public void setObject(Concept concept) {
        setValue(concept.getName());
    }

    private void handleDropEvent(Object data) {
        if (data instanceof Concept) {
            Concept concept = (Concept) data;
            setValue(concept.getName());
        } else {
            showErrorMessage("Not a Concept");
        }
    }

    protected void showErrorMessage(String message) {
        setErrorMessage(message);
        setInvalid(true);
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
