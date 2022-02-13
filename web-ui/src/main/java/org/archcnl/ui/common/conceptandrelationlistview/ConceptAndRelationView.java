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
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteConceptRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteHierarchyObjectRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteRelationRequestedEvent;
import org.archcnl.ui.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.events.ConceptHierarchyMoveRequestedEvent;
import org.archcnl.ui.events.EditorRequestedEvent;
import org.archcnl.ui.events.GridUpdateRequestedEvent;
import org.archcnl.ui.events.HierarchyMoveRequestedEvent;
import org.archcnl.ui.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.events.RelationHierarchyMoveRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RelationEditorRequestedEvent;

public class ConceptAndRelationView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_EXPANSION_DEPTH = 10;

    protected HierarchyView<Concept> conceptHierarchyView;
    protected HierarchyView<Relation> relationHierarchyView;

    public ConceptAndRelationView() {
        setHeightFull();
        initHierarchies();
        createConceptHierarchy();
        createRelationHierarchy();
        addElements();
    }

    // Overriden in the editable version of this
    protected void addElements() {
        add(conceptHierarchyView);
        add(relationHierarchyView);
    }

    protected void createConceptHierarchy() {
        conceptHierarchyView.setHeight(50, Unit.PERCENTAGE);

        conceptHierarchyView.addListener(
                GridUpdateRequestedEvent.class,
                e -> {
                    requestConceptGridUpdate();
                });
        conceptHierarchyView.addListener(
                HierarchyMoveRequestedEvent.class,
                e -> {
                    requestConceptSwap(e);
                });

        // TODO: Caution dirty hack: cast could be replaced by heavy rework, where mappings are not
        // part of concepts but are mapped to each other externally
        conceptHierarchyView.addListener(
                EditorRequestedEvent.class,
                e -> {
                    fireEvent(
                            new ConceptEditorRequestedEvent(
                                    this, true, (CustomConcept) e.getSource().get()));
                });
        conceptHierarchyView.addListener(
                DeleteHierarchyObjectRequestedEvent.class,
                event ->
                        fireEvent(
                                new DeleteConceptRequestedEvent(
                                        conceptHierarchyView,
                                        true,
                                        (Concept) event.getHierarchyObject())));
    }

    protected void initHierarchies() {
        conceptHierarchyView = new HierarchyView<Concept>();
        relationHierarchyView = new HierarchyView<Relation>();
    }

    protected void createRelationHierarchy() {
        relationHierarchyView.setHeight(50, Unit.PERCENTAGE);
        relationHierarchyView.addListener(
                GridUpdateRequestedEvent.class,
                e -> {
                    requestRelationGridUpdate();
                });
        relationHierarchyView.addListener(
                HierarchyMoveRequestedEvent.class,
                e -> {
                    requestRelationSwap(e);
                });

        // TODO: Caution dirty hack: cast could be replaced by heavy rework, where mappings are not
        // part of relations but are mapped to each other externally
        relationHierarchyView.addListener(
                EditorRequestedEvent.class,
                e -> {
                    fireEvent(
                            new RelationEditorRequestedEvent(
                                    this, true, (CustomRelation) e.getSource().get()));
                });
        relationHierarchyView.addListener(
                DeleteHierarchyObjectRequestedEvent.class,
                event ->
                        fireEvent(
                                new DeleteRelationRequestedEvent(
                                        relationHierarchyView,
                                        true,
                                        (Relation) event.getHierarchyObject())));
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

    protected void requestConceptSwap(HierarchyMoveRequestedEvent e) {
        fireEvent(new ConceptHierarchyMoveRequestedEvent(e));
    }

    protected void requestRelationSwap(HierarchyMoveRequestedEvent e) {
        fireEvent(new RelationHierarchyMoveRequestedEvent(e));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
