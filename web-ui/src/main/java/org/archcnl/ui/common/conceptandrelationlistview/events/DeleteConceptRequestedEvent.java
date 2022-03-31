package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class DeleteConceptRequestedEvent extends ComponentEvent<HierarchyView<Concept>> {

    private static final long serialVersionUID = 8376060245715843882L;
    private HierarchyNode entry;

    public DeleteConceptRequestedEvent(
            HierarchyView<Concept> source, boolean fromClient, HierarchyNode concept) {
        super(source, fromClient);
        this.entry = concept;
    }

    public HierarchyNode getConcept() {
        return entry;
    }

    public String getName() {
        return entry.getName();
    }
}
