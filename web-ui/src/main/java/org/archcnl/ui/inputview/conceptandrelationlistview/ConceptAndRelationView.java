package org.archcnl.ui.inputview.conceptandrelationlistview;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.common.Concept;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.ui.inputview.conceptandrelationlistview.mappinglistlayout.ConceptListEntry;
import org.archcnl.ui.inputview.conceptandrelationlistview.mappinglistlayout.CreateNewLayout;
import org.archcnl.ui.inputview.conceptandrelationlistview.mappinglistlayout.MappingListEntry;
import org.archcnl.ui.inputview.conceptandrelationlistview.mappinglistlayout.RelationListEntry;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.OutputViewRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RelationEditorRequestedEvent;

public class ConceptAndRelationView extends VerticalLayout implements PropertyChangeListener {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_EXPANSION_DEPTH = 10;

    private boolean inputSide;
    private CreateNewLayout createNewConceptLayout;
    private CreateNewLayout createNewRelationLayout;
    private MappingListLayout conceptTreeGrid;
    private MappingListLayout relationTreeGrid;

    public ConceptAndRelationView(boolean inputSide) {
        this.inputSide = inputSide;
        createNewConceptLayout =
                new CreateNewLayout(
                        "Concepts",
                        "Create new concept",
                        e -> fireEvent(new ConceptEditorRequestedEvent(this, true)));
        createNewRelationLayout =
                new CreateNewLayout(
                        "Relations",
                        "Create new relation",
                        e -> fireEvent(new RelationEditorRequestedEvent(this, true)));
        RulesConceptsAndRelations.getInstance().getConceptManager().addPropertyChangeListener(this);
        RulesConceptsAndRelations.getInstance()
                .getRelationManager()
                .addPropertyChangeListener(this);
        createNewConceptLayout.setHeight(46, Unit.PERCENTAGE);
        createNewRelationLayout.setHeight(46, Unit.PERCENTAGE);
        updateConceptView();
        updateRelationView();

        add(createNewConceptLayout);
        add(createNewRelationLayout);

        add(
                new Button(
                        "Check for violations",
                        click -> fireEvent(new OutputViewRequestedEvent(this, true))));
        getStyle().set("border", "1px solid black");
    }

    private void updateConceptView() {
<<<<<<< master:web-ui/src/main/java/org/archcnl/ui/inputview/conceptandrelationlistview/ConceptAndRelationView.java
        final List<Concept> concepts =
                RulesConceptsAndRelations.getInstance().getConceptManager().getInputConcepts();
        final List<MappingListEntry> conceptData = new LinkedList<>();
        final ConceptListEntry defaultConceptsStub =
                new ConceptListEntry("Default Concepts", concepts);
=======
        List<Concept> concepts;
        if (inputSide) {
            concepts =
                    RulesConceptsAndRelations.getInstance().getConceptManager().getInputConcepts();
        } else {
            concepts =
                    RulesConceptsAndRelations.getInstance().getConceptManager().getOutputConcepts();
        }
        List<MappingListEntry> conceptData = new LinkedList<>();
        ConceptListEntry defaultConceptsStub = new ConceptListEntry("Default Concepts", concepts);
>>>>>>> Added input/output flag to ConceptAndRelationView:web-ui/src/main/java/org/archcnl/ui/input/ConceptAndRelationView.java
        conceptData.add(defaultConceptsStub);
        if (conceptTreeGrid != null) {
            createNewConceptLayout.remove(conceptTreeGrid);
        }
        conceptTreeGrid = new MappingListLayout(conceptData);
        conceptTreeGrid.addListener(ConceptEditorRequestedEvent.class, this::fireEvent);
        conceptTreeGrid.expandRecursively(
                conceptData, ConceptAndRelationView.DEFAULT_EXPANSION_DEPTH);
        createNewConceptLayout.add(conceptTreeGrid);
    }

    private void updateRelationView() {
<<<<<<< master:web-ui/src/main/java/org/archcnl/ui/inputview/conceptandrelationlistview/ConceptAndRelationView.java
        final List<Relation> relations =
                RulesConceptsAndRelations.getInstance().getRelationManager().getInputRelations();
        final List<MappingListEntry> relationData = new LinkedList<>();
        final RelationListEntry defaultRelationsStub =
=======
        List<Relation> relations;
        if (inputSide) {
            relations =
                    RulesConceptsAndRelations.getInstance()
                            .getRelationManager()
                            .getInputRelations();
        } else {
            relations =
                    RulesConceptsAndRelations.getInstance()
                            .getRelationManager()
                            .getOutputRelations();
        }
        List<MappingListEntry> relationData = new LinkedList<>();
        RelationListEntry defaultRelationsStub =
>>>>>>> Added input/output flag to ConceptAndRelationView:web-ui/src/main/java/org/archcnl/ui/input/ConceptAndRelationView.java
                new RelationListEntry("Default Relations", relations);
        relationData.add(defaultRelationsStub);
        if (relationTreeGrid != null) {
            createNewRelationLayout.remove(relationTreeGrid);
        }
        relationTreeGrid = new MappingListLayout(relationData);
        relationTreeGrid.addListener(RelationEditorRequestedEvent.class, this::fireEvent);
        relationTreeGrid.expandRecursively(
                relationData, ConceptAndRelationView.DEFAULT_EXPANSION_DEPTH);
        createNewRelationLayout.add(relationTreeGrid);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        updateConceptView();
        updateRelationView();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
