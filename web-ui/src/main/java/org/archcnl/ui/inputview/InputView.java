package org.archcnl.ui.inputview;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.inputview.conceptandrelationlistview.ConceptAndRelationView;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.OutputViewRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RelationEditorRequestedEvent;

public class InputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;
    private static final float GOLDEN_RATIO = 61.8f;

    private ConceptAndRelationView conceptAndRelationView;
    private RulesOrMappingEditorView currentlyShownView;

    public InputView(final RulesOrMappingEditorView ruleEditorView) {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        initConceptAndRelationView();

        changeCurrentlyShownView(ruleEditorView);
        addAndExpand(currentlyShownView, conceptAndRelationView);
    }

    public void changeCurrentlyShownView(final RulesOrMappingEditorView newView) {
        newView.setWidth(InputView.GOLDEN_RATIO, Unit.PERCENTAGE);
        replace(currentlyShownView, newView);
        currentlyShownView = newView;
    }

    private void initConceptAndRelationView() {
<<<<<<< master:web-ui/src/main/java/org/archcnl/ui/inputview/InputView.java
        conceptAndRelationView = new ConceptAndRelationView();
        conceptAndRelationView.setWidth(100.0f - InputView.GOLDEN_RATIO, Unit.PERCENTAGE);
=======
        conceptAndRelationView = new ConceptAndRelationView(true);
        conceptAndRelationView.setWidth(100.0f - GOLDEN_RATIO, Unit.PERCENTAGE);
>>>>>>> Added input/output flag to ConceptAndRelationView:web-ui/src/main/java/org/archcnl/ui/input/InputView.java
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
