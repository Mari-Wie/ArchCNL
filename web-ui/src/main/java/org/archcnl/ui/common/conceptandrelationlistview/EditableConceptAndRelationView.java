package org.archcnl.ui.common.conceptandrelationlistview;

import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationEditorRequestedEvent;

public class EditableConceptAndRelationView extends ConceptAndRelationView {
    public EditableConceptAndRelationView() {
        super();
    }

    @Override
    protected void addElements() {
        conceptHierarchyView.createCreateNewLayout(
                "New Concept", e -> fireEvent(new ConceptEditorRequestedEvent(this, true)));
        add(conceptHierarchyView);

        relationHierarchyView.createCreateNewLayout(
                "New Relation",
                e -> fireEvent(new RelationEditorRequestedEvent(this, true)));

        add(relationHierarchyView);
    }

    @Override
    protected void initHierarchies() {
        conceptHierarchyView = new EditableHierarchyView<Concept>();
        relationHierarchyView = new EditableHierarchyView<Relation>();
    }
}
