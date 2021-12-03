package org.archcnl.ui.input;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.archcnl.ui.input.InputContract.Presenter;

public class InputView extends HorizontalLayout implements InputContract.View {

    private static final long serialVersionUID = 1L;
    private static final float GOLDEN_RATIO = 61.8f;

    private ConceptAndRelationView conceptAndRelationView;
    private VerticalLayout currentlyShownView;
    private Presenter presenter;

    public InputView(InputContract.Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        conceptAndRelationView = presenter.createConceptAndRelationView();
        conceptAndRelationView.setWidth(100.0f - GOLDEN_RATIO, Unit.PERCENTAGE);

        changeCurrentlyShownView(presenter.createArchitectureRulesLayout());
        addAndExpand(currentlyShownView, conceptAndRelationView);
    }

    public void newSetup(VerticalLayout newComp) {
        newComp.setWidth(GOLDEN_RATIO, Unit.PERCENTAGE);
        replace(currentlyShownView, newComp);
        currentlyShownView = newComp;
    }

    @Override
    public void changeCurrentlyShownView(RulesOrMappingEditorView newView) {
        ignoreTheOldBS(newView);
    }
}
