package org.archcnl.ui.common.conceptandrelationlistview;

import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationEditorRequestedEvent;

public class EditableConceptAndRelationView extends ConceptAndRelationView {

    private static final long serialVersionUID = 2967841151192415193L;

    @Override
    protected void addElements() {
        conceptHierarchyView.createEditorButton(
                "New Concept", e -> fireEvent(new ConceptEditorRequestedEvent(this, true)));
        add(conceptHierarchyView);

        relationHierarchyView.createEditorButton(
                "New Relation", e -> fireEvent(new RelationEditorRequestedEvent(this, true)));

        add(relationHierarchyView);
    }

    @Override
    protected void initHierarchies() {
        conceptHierarchyView = new EditableHierarchyView<Concept>();
        relationHierarchyView = new EditableHierarchyView<Relation>();
    }
}
