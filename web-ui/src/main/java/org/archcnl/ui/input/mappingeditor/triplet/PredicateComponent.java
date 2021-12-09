package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.ui.input.mappingeditor.events.PredicateSelectedEvent;
import org.archcnl.ui.input.mappingeditor.exceptions.RelationNotDefinedException;

public class PredicateComponent extends ComboBox<String> implements DropTarget<PredicateComponent> {

    private static final long serialVersionUID = -5423813782732362932L;
    private ObjectView objectView;

    public PredicateComponent(ObjectView objectView) {
        this.objectView = objectView;
        setActive(true);
        setPlaceholder("Relation");
        updateItems();
        setClearButtonVisible(true);

        addValueChangeListener(e -> fireEvent(new PredicateSelectedEvent(this, false)));
        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
    }

    public void updateItems() {
        setItems(this.getRelationNames());
    }

    private Optional<String> getSelectedItem() {
        return Optional.ofNullable(getValue());
    }

    public List<String> getRelationNames() {
        return RulesConceptsAndRelations.getInstance()
                .getRelationManager()
                .getInputRelations()
                .stream()
                .map(Relation::getName)
                .collect(Collectors.toList());
    }

    public Relation getPredicate()
            throws RelationDoesNotExistException, RelationNotDefinedException {
        String relationName = getSelectedItem().orElseThrow(RelationNotDefinedException::new);
        return RulesConceptsAndRelations.getInstance()
                .getRelationManager()
                .getRelationByName(relationName);
    }

    public void setPredicate(Relation predicate) {
        setValue(predicate.getName());
    }

    public void handleDropEvent(Object data) {
        if (data instanceof Relation) {
            Relation relation = (Relation) data;
            setPredicate(relation);
        } else {
            showErrorMessage("Not a Relation");
        }
    }

    // TODO: move to presenter
    public void valueHasChanged() {
        Relation relation = null;
        try {
            String newValue = getSelectedItem().orElseThrow(NoSuchElementException::new);
            relation =
                    RulesConceptsAndRelations.getInstance()
                            .getRelationManager()
                            .getRelationByName(newValue);
        } catch (RelationDoesNotExistException | NoSuchElementException e) {
            // leave relation == null
        }
        objectView.predicateHasChanged(Optional.ofNullable(relation));
    }

    public void highlightWhenEmpty() {
        try {
            getPredicate();
        } catch (RelationDoesNotExistException e) {
            showErrorMessage("Relation does not exist");
        } catch (RelationNotDefinedException e) {
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
