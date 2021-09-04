package org.archcnl.ui.input;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ArchitectureRulesLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    CreateNewLayout createNewRuleLayout =
            new CreateNewLayout("Architecture Rules", "Create new Arch Rule");
    VerticalLayout rulesLayout = new VerticalLayout();

    public ArchitectureRulesLayout() {
        add(createNewRuleLayout);
        add(rulesLayout);
        getStyle().set("border", "1px solid black");
    }
}
