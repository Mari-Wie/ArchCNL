package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.ui.common.andtriplets.triplet.ConceptSelectionComponent;

public class ConceptSelectedEvent extends ComponentEvent<ConceptSelectionComponent> {

    private static final long serialVersionUID = 1L;

    public ConceptSelectedEvent(final ConceptSelectionComponent source, final boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(final ConceptManager conceptManager) {
        final Optional<String> value = getSource().getSelectedItem();
        if (value.isPresent()) {
            final Optional<Concept> conceptOpt = conceptManager.getConceptByName(value.get());
            if (conceptOpt.isEmpty()) {
                getSource().showErrorMessage("Concept does not exist");
            }
        }
    }
}
