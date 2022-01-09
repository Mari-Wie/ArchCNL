package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.ConceptSelectionComponent;

public class ConceptSelectedEvent extends ComponentEvent<ConceptSelectionComponent> {

    private static final long serialVersionUID = 8376134914773624L;

    public ConceptSelectedEvent(ConceptSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(ConceptManager conceptManager) {
        Optional<String> value = getSource().getSelectedItem();
        if (value.isPresent()) {
            String name = value.get();
            try {
                conceptManager.getConceptByName(name);
            } catch (ConceptDoesNotExistException e) {
                getSource().showErrorMessage("Concept does not exist");
            }
        }
    }
}
