package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.common.Concept;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.domain.common.Relation;
import org.archcnl.ui.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.events.EditorRequestedEvent;
import org.archcnl.ui.events.GridUpdateRequestedEvent;
import org.archcnl.ui.events.HierarchySwapRequestedEvent;
import org.archcnl.ui.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.events.RelationHierarchySwapRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RelationEditorRequestedEvent;

public class ConceptAndRelationView extends RulesOrMappingEditorView {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_EXPANSION_DEPTH = 10;

    private HierarchyView<Concept> hv1;
    private HierarchyView<Relation> hv2;

    public ConceptAndRelationView(boolean inputSide) {
        createConceptHierarchy();
        createRelationHierarchy();
    }

    private void createConceptHierarchy() {
        hv1 = new EditableHierarchyView<Concept>();
        hv1.setHeight(50, Unit.PERCENTAGE);

        hv1.addListener(
                GridUpdateRequestedEvent.class,
                e -> {
                    requestConceptGridUpdate();
                });
        hv1.addListener(
                HierarchySwapRequestedEvent.class,
                e -> {
                    requestConceptSwap(e);
                });

        hv1.createCreateNewLayout(
                "Concepts",
                "Create New Concept",
                e -> {
                    fireEvent(new ConceptEditorRequestedEvent(this, true));
                });

        // TODO: Caution dirty hack: cast could be replaced by heavy rework, where mappings are not
        // part of concepts but are mapped to each other externally
        hv1.addListener(
                EditorRequestedEvent.class,
                e -> {
                    fireEvent(
                            new ConceptEditorRequestedEvent(
                                    this, true, (CustomConcept) e.getSource().get()));
                });
        add(hv1);
    }

    private void createRelationHierarchy() {
        hv2 = new EditableHierarchyView<Relation>();
        hv2.setHeight(50, Unit.PERCENTAGE);
        hv2.addListener(
                GridUpdateRequestedEvent.class,
                e -> {
                    requestRelationGridUpdate();
                });
        hv2.addListener(
                HierarchySwapRequestedEvent.class,
                e -> {
                    requestRelationSwap(e);
                });
        hv2.createCreateNewLayout(
                "Relations",
                "Create New Relation",
                e -> {
                    fireEvent(new RelationEditorRequestedEvent(this, true));
                });

        // TODO: Caution dirty hack: cast could be replaced by heavy rework, where mappings are not
        // part of concepts but are mapped to each other externally
        hv2.addListener(
                EditorRequestedEvent.class,
                e -> {
                    fireEvent(
                            new RelationEditorRequestedEvent(
                                    this, true, (CustomRelation) e.getSource().get()));
                });
        add(hv2);
    }

    public void update() {
        hv1.requestGridUpdate();
        hv2.requestGridUpdate();
    }

    private void requestConceptGridUpdate() {
        System.out.println("Fireing ConceptGridUpdateRequestedEvent");
        fireEvent(new ConceptGridUpdateRequestedEvent(hv1, true));
    }

    private void requestRelationGridUpdate() {
        System.out.println("Fireing RelationGridUpdateRequestedEvent");
        fireEvent(new RelationGridUpdateRequestedEvent(hv2, true));
    }

    private void requestConceptSwap(HierarchySwapRequestedEvent e) {
        fireEvent(new ConceptHierarchySwapRequestedEvent(e));
    }

    private void requestRelationSwap(HierarchySwapRequestedEvent e) {
        fireEvent(new RelationHierarchySwapRequestedEvent(e));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
