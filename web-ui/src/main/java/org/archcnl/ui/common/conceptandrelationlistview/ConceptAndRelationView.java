package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteConceptRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteHierarchyObjectRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteRelationRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.EditorRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.GridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.HierarchySwapRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.NodeAddRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationEditorRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationHierarchySwapRequestedEvent;

public class ConceptAndRelationView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    protected HierarchyView<Concept> conceptHierarchyView;
    protected HierarchyView<Relation> relationHierarchyView;

    public ConceptAndRelationView() {
        setHeightFull();
        initHierarchies();
        createConceptHierarchy();
        createRelationHierarchy();
        addElements();
        addNodeButton();
    }

    private void addNodeButton() {
        conceptHierarchyView.createEditorButton(
                "Add Node", e -> conceptHierarchyView.addTextField());
        relationHierarchyView.createEditorButton(
                "Add Node", e -> relationHierarchyView.addTextField());
    }

    // Overriden in the editable version of this
    protected void addElements() {
        add(conceptHierarchyView);
        add(relationHierarchyView);
    }

    protected void createConceptHierarchy() {
        conceptHierarchyView.setHeight(50, Unit.PERCENTAGE);

        conceptHierarchyView.addListener(
                GridUpdateRequestedEvent.class, e -> requestConceptGridUpdate());
        conceptHierarchyView.addListener(
                HierarchySwapRequestedEvent.class, this::requestConceptSwap);
        conceptHierarchyView.addListener(
                NodeAddRequestedEvent.class,
                e -> {
                    e.setNodeType(NodeAddRequestedEvent.NodeType.CONCEPT);
                    fireEvent(e);
                });

        // TODO: Caution dirty hack: cast could be replaced by heavy rework, where mappings are not
        // part of concepts but are mapped to each other externally
        conceptHierarchyView.addListener(
                EditorRequestedEvent.class,
                e ->
                        fireEvent(
                                new ConceptEditorRequestedEvent(
                                        this, true, (CustomConcept) e.getSource().get())));
        conceptHierarchyView.addListener(
                DeleteHierarchyObjectRequestedEvent.class,
                event ->
                        fireEvent(
                                new DeleteConceptRequestedEvent(
                                        conceptHierarchyView, true, event.getHierarchyNode())));
    }

    protected void initHierarchies() {
        conceptHierarchyView = new HierarchyView<>();
        relationHierarchyView = new HierarchyView<>();
    }

    protected void createRelationHierarchy() {
        relationHierarchyView.setHeight(50, Unit.PERCENTAGE);
        relationHierarchyView.addListener(
                GridUpdateRequestedEvent.class, e -> requestRelationGridUpdate());
        relationHierarchyView.addListener(
                HierarchySwapRequestedEvent.class, this::requestRelationSwap);

        relationHierarchyView.addListener(
                NodeAddRequestedEvent.class,
                e -> {
                    e.setNodeType(NodeAddRequestedEvent.NodeType.RELATION);
                    fireEvent(e);
                });
        // TODO: Caution dirty hack: cast could be replaced by heavy rework, where mappings are not
        // part of relations but are mapped to each other externally
        relationHierarchyView.addListener(
                EditorRequestedEvent.class,
                e ->
                        fireEvent(
                                new RelationEditorRequestedEvent(
                                        this, true, (CustomRelation) e.getSource().get())));
        relationHierarchyView.addListener(
                DeleteHierarchyObjectRequestedEvent.class,
                event ->
                        fireEvent(
                                new DeleteRelationRequestedEvent(
                                        relationHierarchyView, true, event.getHierarchyNode())));
    }

    public void update() {
        relationHierarchyView.requestGridUpdate();
        conceptHierarchyView.requestGridUpdate();
    }

    protected void requestConceptGridUpdate() {
        fireEvent(new ConceptGridUpdateRequestedEvent(conceptHierarchyView, true));
    }

    protected void requestRelationGridUpdate() {
        fireEvent(new RelationGridUpdateRequestedEvent(relationHierarchyView, true));
    }

    protected void requestConceptSwap(HierarchySwapRequestedEvent<Concept> e) {
        fireEvent(new ConceptHierarchySwapRequestedEvent(e));
    }

    protected void requestRelationSwap(HierarchySwapRequestedEvent<Relation> e) {
        fireEvent(new RelationHierarchySwapRequestedEvent(e));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
