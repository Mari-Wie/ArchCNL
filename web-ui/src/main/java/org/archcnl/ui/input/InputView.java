package org.archcnl.ui.input;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.domain.input.model.mappings.CustomConcept;
import org.archcnl.domain.input.model.mappings.CustomRelation;
import org.archcnl.ui.input.mappingeditor.ConceptEditorPresenter;
import org.archcnl.ui.input.mappingeditor.ConceptEditorView;
import org.archcnl.ui.input.mappingeditor.MappingEditorView;
import org.archcnl.ui.input.mappingeditor.RelationEditorPresenter;
import org.archcnl.ui.input.mappingeditor.RelationEditorView;
import org.archcnl.ui.input.mappingeditor.RulesOrMappingEditorView;

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
        ConceptEditorPresenter conceptEditorPresenter = new ConceptEditorPresenter();
        MappingEditorView view = new ConceptEditorView(conceptEditorPresenter, this);
        changeCurrentlyShownView(view);
    }

    public void switchToConceptEditorView(CustomConcept concept) {
        ConceptEditorPresenter conceptEditorPresenter = new ConceptEditorPresenter(concept);
        MappingEditorView view = new ConceptEditorView(conceptEditorPresenter, this);
        changeCurrentlyShownView(view);
    }

    public void switchToRelationEditorView() {
        RelationEditorPresenter relationEditorPresenter = new RelationEditorPresenter();
        MappingEditorView view = new RelationEditorView(relationEditorPresenter, this);
        changeCurrentlyShownView(view);
    }

    public void switchToRelationEditorView(CustomRelation relation) {
        RelationEditorPresenter relationEditorPresenter = new RelationEditorPresenter(relation);
        MappingEditorView view = new RelationEditorView(relationEditorPresenter, this);
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
