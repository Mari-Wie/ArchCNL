package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class DeleteConceptRequestedEvent extends ComponentEvent<HierarchyView<Concept>> {

    private static final long serialVersionUID = 8376060245715843882L;
    private Concept concept;

    public DeleteConceptRequestedEvent(
            HierarchyView<Concept> source, boolean fromClient, Concept concept) {
        super(source, fromClient);
        this.concept = concept;
    }

    public Concept getConcept() {
        return concept;
    }
}
