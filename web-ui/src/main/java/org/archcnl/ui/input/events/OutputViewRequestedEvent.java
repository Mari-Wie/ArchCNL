package org.archcnl.ui.input.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.ConceptAndRelationView;

public class OutputViewRequestedEvent extends ComponentEvent<ConceptAndRelationView> {

    private static final long serialVersionUID = -9209981613958108844L;

    public OutputViewRequestedEvent(ConceptAndRelationView source, boolean fromClient) {
        super(source, fromClient);
    }
}
