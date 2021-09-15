package org.archcnl.ui.input;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.ui.input.mappingeditor.ConceptCreationView;
import org.archcnl.ui.input.mappingeditor.RelationCreationView;
import org.archcnl.ui.input.mappingeditor.RulesOrMappingCreationView;

public class InputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;
    private static final float GOLDEN_RATIO = 61.8f;

    private ConceptAndRelationView conceptAndRelationView = new ConceptAndRelationView(this);
    private RulesOrMappingCreationView architectureRulesView = new ArchitectureRulesLayout();
    private RulesOrMappingCreationView conceptCreationView = new ConceptCreationView(this);
    private RulesOrMappingCreationView relationCreationView = new RelationCreationView(this);
    private RulesOrMappingCreationView currentlyShownView;

    public InputView() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        conceptAndRelationView.setWidth(100.0f - GOLDEN_RATIO, Unit.PERCENTAGE);

        changeCurrentlyShownLayout(architectureRulesView);
        addAndExpand(currentlyShownView, conceptAndRelationView);
    }

    public void switchToConceptCreationLayout() {
        changeCurrentlyShownLayout(conceptCreationView);
    }

    public void switchToRelationCreationLayout() {
        changeCurrentlyShownLayout(relationCreationView);
    }

    public void switchToArchitectureRulesLayout() {
        changeCurrentlyShownLayout(architectureRulesView);
    }

    private void changeCurrentlyShownLayout(RulesOrMappingCreationView newView) {
        newView.setWidth(GOLDEN_RATIO, Unit.PERCENTAGE);
        replace(currentlyShownView, newView);
        currentlyShownView = newView;
    }
}
