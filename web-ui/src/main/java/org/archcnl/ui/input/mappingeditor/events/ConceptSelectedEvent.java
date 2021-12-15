package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.triplet.ConceptSelectionComponent;

public class ConceptSelectedEvent extends ComponentEvent<ConceptSelectionComponent> {

    private static final long serialVersionUID = 8376134914773624L;

    public ConceptSelectedEvent(ConceptSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
