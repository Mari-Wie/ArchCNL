package org.archcnl.ui.common.conceptandrelationlistview;

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
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.ui.common.conceptandrelationlistview.mappinglistlayout.ConceptListEntry;
import org.archcnl.ui.common.conceptandrelationlistview.mappinglistlayout.CreateNewLayout;
import org.archcnl.ui.common.conceptandrelationlistview.mappinglistlayout.MappingListEntry;
import org.archcnl.ui.common.conceptandrelationlistview.mappinglistlayout.RelationListEntry;
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

        getStyle().set("border", "1px solid black");
        createNewConceptLayout =
                new CreateNewLayout(
                        "Concepts",
                        "Create new concept",
                        e -> fireEvent(new ConceptEditorRequestedEvent(this, true)),
                        inputSide);
        createNewRelationLayout =
                new CreateNewLayout(
                        "Relations",
                        "Create new relation",
                        e -> fireEvent(new RelationEditorRequestedEvent(this, true)),
                        inputSide);
        RulesConceptsAndRelations.getInstance().getConceptManager().addPropertyChangeListener(this);
        RulesConceptsAndRelations.getInstance()
                .getRelationManager()
                .addPropertyChangeListener(this);
        createNewConceptLayout.setHeight(inputSide ? 46 : 49, Unit.PERCENTAGE);
        createNewRelationLayout.setHeight(inputSide ? 46 : 49, Unit.PERCENTAGE);
        updateConceptView();
        updateRelationView();

        add(createNewConceptLayout);
        add(createNewRelationLayout);

        if (inputSide) {
            add(
                    new Button(
                            "Check for violations",
                            click -> fireEvent(new OutputViewRequestedEvent(this, true))));
        }
    }

    private void updateConceptView() {
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
        conceptData.add(defaultConceptsStub);
        if (conceptTreeGrid != null) {
            createNewConceptLayout.remove(conceptTreeGrid);
        }
        conceptTreeGrid = new MappingListLayout(conceptData, inputSide);
        conceptTreeGrid.addListener(ConceptEditorRequestedEvent.class, this::fireEvent);
        conceptTreeGrid.expandRecursively(
                conceptData, ConceptAndRelationView.DEFAULT_EXPANSION_DEPTH);
        createNewConceptLayout.add(conceptTreeGrid);
    }

    private void updateRelationView() {
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
                new RelationListEntry("Default Relations", relations);
        relationData.add(defaultRelationsStub);
        if (relationTreeGrid != null) {
            createNewRelationLayout.remove(relationTreeGrid);
        }
        relationTreeGrid = new MappingListLayout(relationData, inputSide);
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
