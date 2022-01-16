package org.archcnl.ui.inputview.rulesormappingeditorview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.conceptandrelationlistview.ConceptAndRelationView;

public class OutputViewRequestedEvent extends ComponentEvent<ConceptAndRelationView> {

    private static final long serialVersionUID = -9209981613958108844L;

    public OutputViewRequestedEvent(final ConceptAndRelationView source, final boolean fromClient) {
        super(source, fromClient);
    }
}
