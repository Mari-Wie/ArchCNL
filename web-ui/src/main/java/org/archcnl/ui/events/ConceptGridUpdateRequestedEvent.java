package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.Concept;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class ConceptGridUpdateRequestedEvent extends ComponentEvent<HierarchyView<Concept>> {

    private static final long serialVersionUID = -3711307621533570697L;

    public ConceptGridUpdateRequestedEvent(
            final HierarchyView<Concept> source, final boolean fromClient) {
        super(source, fromClient);
    }
}
