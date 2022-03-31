package org.archcnl.ui.inputview;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.common.conceptandrelationlistview.ConceptAndRelationView;
import org.archcnl.ui.common.conceptandrelationlistview.EditableConceptAndRelationView;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteConceptRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteRelationRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.NodeAddRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationEditorRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationHierarchySwapRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.OutputViewRequestedEvent;

public class InputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;
    private static final float CONTENT_RATIO = 70.0f;

    private ConceptAndRelationView conceptAndRelationView;
    private RulesOrMappingEditorView currentlyShownView;
    // TODO: rename, its just for spacing stuff
    private VerticalLayout rightHandSideLayout;

    public InputView(final RulesOrMappingEditorView ruleEditorView) {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        initConceptAndRelationView();
        Button checkViolationButton =
                new Button(
                        "Check for violations",
                        click -> fireEvent(new OutputViewRequestedEvent(this, true)));
        checkViolationButton.setClassName("check-violation-btn");
        initRightHandSideLayout(conceptAndRelationView, checkViolationButton);

        changeCurrentlyShownView(ruleEditorView);
        addAndExpand(currentlyShownView, rightHandSideLayout);
    }

    public void initRightHandSideLayout(ConceptAndRelationView hierarchies, Button but) {
        rightHandSideLayout = new VerticalLayout();

        rightHandSideLayout.getStyle().remove("border");
        rightHandSideLayout.setPadding(false);
        rightHandSideLayout.setWidth(100.0f - CONTENT_RATIO, Unit.PERCENTAGE);
        rightHandSideLayout.setHeight(100, Unit.PERCENTAGE);

        rightHandSideLayout.add(hierarchies, but);
    }

    public void changeCurrentlyShownView(final RulesOrMappingEditorView newView) {
        newView.setWidth(InputView.CONTENT_RATIO, Unit.PERCENTAGE);
        replace(currentlyShownView, newView);
        conceptAndRelationView.update();
        currentlyShownView = newView;
    }

    private void initConceptAndRelationView() {
        conceptAndRelationView = new EditableConceptAndRelationView();
        conceptAndRelationView.addListener(ConceptEditorRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(RelationEditorRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(ConceptGridUpdateRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(RelationGridUpdateRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(NodeAddRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(OutputViewRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(
                ConceptHierarchySwapRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(
                RelationHierarchySwapRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(DeleteConceptRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(DeleteRelationRequestedEvent.class, this::fireEvent);
    }

    public void updateConceptAndRelations() {
        conceptAndRelationView.update();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
