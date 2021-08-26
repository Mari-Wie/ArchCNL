package org.vaadin.example.inputview;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class InputView extends HorizontalLayout {
    ArchitectureRulesLayout architectureRulesLayout = new ArchitectureRulesLayout();
    ConceptAndRelationView conceptAndRelationView = new ConceptAndRelationView();

    public InputView() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        // golden ratio??
        architectureRulesLayout.setWidth(61.8f, Unit.PERCENTAGE);
        conceptAndRelationView.setWidth(100.0f - 61.8f, Unit.PERCENTAGE);

        addAndExpand(architectureRulesLayout, conceptAndRelationView);
    }
}
