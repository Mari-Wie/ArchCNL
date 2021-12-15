package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateComponent;

public class RelationListUpdateRequestedEvent extends ComponentEvent<PredicateComponent> {

    private static final long serialVersionUID = 4804740257464327897L;

    public RelationListUpdateRequestedEvent(PredicateComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
