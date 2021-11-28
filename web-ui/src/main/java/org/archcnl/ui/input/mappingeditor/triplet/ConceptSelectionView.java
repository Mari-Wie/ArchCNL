package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dnd.DropTarget;
import java.util.stream.Collectors;
import org.archcnl.domain.common.Concept;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;

public class ConceptSelectionView extends ComboBox<String>
        implements DropTarget<ConceptSelectionView> {

    private static final long serialVersionUID = 5874026321476515429L;

    public ConceptSelectionView() {
        setActive(true);
        setLabel("Object");
        setPlaceholder("Concept");
        updateItems();
        setClearButtonVisible(true);

        addValueChangeListener(
                event -> {
                    // TODO: Check if predicate can relate to this concept
                    setInvalid(false);
                });
        addDropListener(event -> event.getDragData().ifPresent(this::handleDropEvent));
    }

    public void updateItems() {
        setItems(
                RulesConceptsAndRelations.getInstance()
                        .getConceptManager()
                        .getInputConcepts()
                        .stream()
                        .map(Concept::getName)
                        .collect(Collectors.toList()));
    }

    public Concept getObject() throws ConceptDoesNotExistException, ObjectNotDefinedException {
        String conceptName = getValue();
        if (conceptName != null) {
            return RulesConceptsAndRelations.getInstance()
                    .getConceptManager()
                    .getConceptByName(conceptName);
        }
        throw new ObjectNotDefinedException();
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
}
