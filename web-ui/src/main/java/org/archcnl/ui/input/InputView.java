package org.archcnl.ui.input;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.input.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.input.events.OutputViewRequestedEvent;
import org.archcnl.ui.input.events.RelationEditorRequestedEvent;

public class InputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;
    private static final float GOLDEN_RATIO = 61.8f;

    private ConceptAndRelationView conceptAndRelationView;
    private RulesOrMappingEditorView currentlyShownView;

    public InputView(RulesOrMappingEditorView ruleEditorView) {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        initConceptAndRelationView();

        changeCurrentlyShownView(ruleEditorView);
        addAndExpand(currentlyShownView, conceptAndRelationView);
    }

    public void changeCurrentlyShownView(RulesOrMappingEditorView newView) {
        newView.setWidth(GOLDEN_RATIO, Unit.PERCENTAGE);
        replace(currentlyShownView, newView);
        currentlyShownView = newView;
    }

    private void initConceptAndRelationView() {
        conceptAndRelationView = new ConceptAndRelationView();
        conceptAndRelationView.setWidth(100.0f - GOLDEN_RATIO, Unit.PERCENTAGE);
        conceptAndRelationView.addListener(ConceptEditorRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(RelationEditorRequestedEvent.class, this::fireEvent);
        conceptAndRelationView.addListener(OutputViewRequestedEvent.class, this::fireEvent);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
