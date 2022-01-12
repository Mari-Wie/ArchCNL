package org.archcnl.ui.input;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.ui.input.InputContract.Presenter;

public class InputView extends HorizontalLayout implements InputContract.View {

    private static final long serialVersionUID = 1L;
    private static final float GOLDEN_RATIO = 75.0f;

    private ConceptAndRelationView conceptAndRelationView;
    private RulesOrMappingEditorView currentlyShownView;
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

    @Override
    public void changeCurrentlyShownView(RulesOrMappingEditorView newView) {
        newView.setWidth(GOLDEN_RATIO, Unit.PERCENTAGE);
        replace(currentlyShownView, newView);
        currentlyShownView = newView;
    }
}
