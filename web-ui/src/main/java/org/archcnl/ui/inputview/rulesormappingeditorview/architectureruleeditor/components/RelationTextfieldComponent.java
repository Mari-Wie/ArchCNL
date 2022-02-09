package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.Relation;

public class RelationTextfieldComponent extends TextField
        implements DropTarget<ConceptTextfieldComponent> {

    private static final long serialVersionUID = 1L;

    public RelationTextfieldComponent() {
        setActive(true);
        setPlaceholder("Relation");
        setLabel("Relation");
        setClearButtonVisible(true);

        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
    }

    public Optional<String> getSelectedItem() {
        return getOptionalValue();
    }

    private void handleDropEvent(Object data) {
        if (data instanceof Relation) {
            Relation concept = (Relation) data;
            setValue(concept.getName());
        } else {
            showErrorMessage("Not a Relation");
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
