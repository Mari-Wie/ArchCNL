package org.archcnl.ui.input;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.ui.input.mappingeditor.ConceptCreationView;
import org.archcnl.ui.input.mappingeditor.MappingCreationPresenter;
import org.archcnl.ui.input.mappingeditor.MappingCreationView;
import org.archcnl.ui.input.mappingeditor.RelationCreationView;
import org.archcnl.ui.input.mappingeditor.RulesOrMappingCreationView;

public class InputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;
    private static final float GOLDEN_RATIO = 61.8f;

    private ConceptAndRelationView conceptAndRelationView = new ConceptAndRelationView(this);
    private RulesOrMappingCreationView architectureRulesView = new ArchitectureRulesLayout();
    private RulesOrMappingCreationView currentlyShownView;

    public InputView() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        conceptAndRelationView.setWidth(100.0f - GOLDEN_RATIO, Unit.PERCENTAGE);

        changeCurrentlyShownView(architectureRulesView);
        addAndExpand(currentlyShownView, conceptAndRelationView);
    }

    public void switchToConceptCreationView() {
        MappingCreationView view = new ConceptCreationView(this);
        new MappingCreationPresenter(view);
        changeCurrentlyShownView(view);
    }

    public void switchToRelationCreationView() {
        MappingCreationView view = new RelationCreationView(this);
        new MappingCreationPresenter(view);
        changeCurrentlyShownView(view);
    }

    public void switchToArchitectureRulesView() {
        changeCurrentlyShownView(architectureRulesView);
    }

    private void changeCurrentlyShownView(RulesOrMappingCreationView newView) {
        newView.setWidth(GOLDEN_RATIO, Unit.PERCENTAGE);
        replace(currentlyShownView, newView);
        currentlyShownView = newView;
    }
}
