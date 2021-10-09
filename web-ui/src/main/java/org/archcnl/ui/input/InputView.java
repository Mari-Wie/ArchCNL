package org.archcnl.ui.input;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.ui.main.MainPresenter;

public class InputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    ArchitectureRulesLayout architectureRulesLayout;
    ConceptAndRelationView conceptAndRelationView;

    public InputView(MainPresenter mainPresenter) {
        architectureRulesLayout = new ArchitectureRulesLayout();
        conceptAndRelationView = new ConceptAndRelationView(mainPresenter);
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        // golden ratio??
        architectureRulesLayout.setWidth(61.8f, Unit.PERCENTAGE);
        conceptAndRelationView.setWidth(100.0f - 61.8f, Unit.PERCENTAGE);

        addAndExpand(architectureRulesLayout, conceptAndRelationView);
    }
}
