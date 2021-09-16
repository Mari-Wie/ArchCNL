package org.archcnl.ui.input;

import org.archcnl.ui.input.mappingeditor.ConceptEditorView;
import org.archcnl.ui.input.mappingeditor.MappingEditorPresenter;
import org.archcnl.ui.input.mappingeditor.MappingEditorView;
import org.archcnl.ui.input.mappingeditor.RelationEditorView;
import org.archcnl.ui.input.mappingeditor.RulesOrMappingEditorView;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class InputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;
    private static final float GOLDEN_RATIO = 61.8f;

    private ConceptAndRelationView conceptAndRelationView = new ConceptAndRelationView(this);
    private RulesOrMappingEditorView architectureRulesView = new ArchitectureRulesLayout();
    private RulesOrMappingEditorView currentlyShownView;

    public InputView() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        conceptAndRelationView.setWidth(100.0f - GOLDEN_RATIO, Unit.PERCENTAGE);

        changeCurrentlyShownView(architectureRulesView);
        addAndExpand(currentlyShownView, conceptAndRelationView);
    }

    public void switchToConceptEditorView() {
        MappingEditorView view = new ConceptEditorView(this);
        new MappingEditorPresenter(view);
        changeCurrentlyShownView(view);
    }

    public void switchToRelationEditorView() {
        MappingEditorView view = new RelationEditorView(this);
        new MappingEditorPresenter(view);
        changeCurrentlyShownView(view);
    }

    public void switchToArchitectureRulesView() {
        changeCurrentlyShownView(architectureRulesView);
    }

    private void changeCurrentlyShownView(RulesOrMappingEditorView newView) {
        newView.setWidth(GOLDEN_RATIO, Unit.PERCENTAGE);
        replace(currentlyShownView, newView);
        currentlyShownView = newView;
    }
}
