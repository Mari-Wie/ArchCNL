package org.vaadin.example.inputview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ArchitectureRulesLayout extends VerticalLayout {
    CreateNewLayout createNewRuleLayout =
            new CreateNewLayout("Architecture Rules", "Create new Arch Rule");
    VerticalLayout rulesLayout = new VerticalLayout();

    public ArchitectureRulesLayout() {
        add(createNewRuleLayout);
        add(rulesLayout);
        getStyle().set("border", "1px solid black");
    }
}