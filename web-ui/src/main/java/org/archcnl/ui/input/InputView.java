package org.archcnl.ui.input;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.ui.input.components.ConceptCreationLayout;
import org.archcnl.ui.input.components.RelationCreationLayout;
import org.archcnl.ui.input.components.RulesOrMappingCreationLayout;

public class InputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;
    private static final float GOLDEN_RATIO = 61.8f;

    private ConceptAndRelationView conceptAndRelationView = new ConceptAndRelationView(this);
    private RulesOrMappingCreationLayout architectureRulesLayout = new ArchitectureRulesLayout();
    private RulesOrMappingCreationLayout conceptCreationLayout = new ConceptCreationLayout(this);
    private RulesOrMappingCreationLayout relationCreationLayout = new RelationCreationLayout(this);
    private RulesOrMappingCreationLayout currentlyShownLayout;

    public InputView() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        conceptAndRelationView.setWidth(100.0f - GOLDEN_RATIO, Unit.PERCENTAGE);

        changeCurrentlyShownLayout(architectureRulesLayout);
        addAndExpand(currentlyShownLayout, conceptAndRelationView);
    }

    public void switchToConceptCreationLayout() {
        changeCurrentlyShownLayout(conceptCreationLayout);
    }

    public void switchToRelationCreationLayout() {
        changeCurrentlyShownLayout(relationCreationLayout);
    }

    public void switchToArchitectureRulesLayout() {
        changeCurrentlyShownLayout(architectureRulesLayout);
    }

    private void changeCurrentlyShownLayout(RulesOrMappingCreationLayout newLayout) {
        newLayout.setWidth(GOLDEN_RATIO, Unit.PERCENTAGE);
        replace(currentlyShownLayout, newLayout);
        currentlyShownLayout = newLayout;
    }
}
