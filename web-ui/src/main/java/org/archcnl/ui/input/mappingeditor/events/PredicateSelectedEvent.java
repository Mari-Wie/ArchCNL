package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateComponent;

public class PredicateSelectedEvent extends ComponentEvent<PredicateComponent> {

    private static final long serialVersionUID = -5512289455282443156L;

    public PredicateSelectedEvent(PredicateComponent source, boolean fromClient) {
        super(source, fromClient);
    }
}
