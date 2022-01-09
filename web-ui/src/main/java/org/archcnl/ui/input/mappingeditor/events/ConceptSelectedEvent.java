package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.ui.input.mappingeditor.triplet.ConceptSelectionComponent;

public class ConceptSelectedEvent extends ComponentEvent<ConceptSelectionComponent> {

    private static final long serialVersionUID = 8376134914773624L;

    public ConceptSelectedEvent(ConceptSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(ConceptManager conceptManager) {
        Optional<String> value = getSource().getSelectedItem();
        if (value.isPresent()) {
            String name = value.get();
            conceptManager.getConceptByName(name);
        }
    }
}
