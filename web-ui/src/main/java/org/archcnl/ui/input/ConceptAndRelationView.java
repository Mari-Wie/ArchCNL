package org.archcnl.ui.input;

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
import org.archcnl.ui.input.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.input.events.OutputViewRequestedEvent;
import org.archcnl.ui.input.events.RelationEditorRequestedEvent;

public class ConceptAndRelationView extends VerticalLayout implements PropertyChangeListener {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_EXPANSION_DEPTH = 10;

    private CreateNewLayout createNewConceptLayout;
    private CreateNewLayout createNewRelationLayout;
    private MappingListLayout conceptTreeGrid;
    private MappingListLayout relationTreeGrid;

    public ConceptAndRelationView() {
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
        List<Concept> concepts =
                RulesConceptsAndRelations.getInstance().getConceptManager().getInputConcepts();
        List<MappingListEntry> conceptData = new LinkedList<>();
        ConceptListEntry defaultConceptsStub = new ConceptListEntry("Default Concepts", concepts);
        conceptData.add(defaultConceptsStub);
        if (conceptTreeGrid != null) {
            createNewConceptLayout.remove(conceptTreeGrid);
        }
        conceptTreeGrid = new MappingListLayout(conceptData);
        conceptTreeGrid.addListener(ConceptEditorRequestedEvent.class, this::fireEvent);
        conceptTreeGrid.expandRecursively(conceptData, DEFAULT_EXPANSION_DEPTH);
        createNewConceptLayout.add(conceptTreeGrid);
    }

    private void updateRelationView() {
        List<Relation> relations =
                RulesConceptsAndRelations.getInstance().getRelationManager().getInputRelations();
        List<MappingListEntry> relationData = new LinkedList<>();
        RelationListEntry defaultRelationsStub =
                new RelationListEntry("Default Relations", relations);
        relationData.add(defaultRelationsStub);
        if (relationTreeGrid != null) {
            createNewRelationLayout.remove(relationTreeGrid);
        }
        relationTreeGrid = new MappingListLayout(relationData);
        relationTreeGrid.addListener(RelationEditorRequestedEvent.class, this::fireEvent);
        relationTreeGrid.expandRecursively(relationData, DEFAULT_EXPANSION_DEPTH);
        createNewRelationLayout.add(relationTreeGrid);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateConceptView();
        updateRelationView();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
