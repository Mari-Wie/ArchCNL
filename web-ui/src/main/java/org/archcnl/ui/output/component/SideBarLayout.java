package org.archcnl.ui.output.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.archcnl.ui.main.MainPresenter;

public class SideBarLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    QueryView parent;
    Button gotoQueryViewButton = new Button("General Information", e -> parent.switchToQueryView());
    Button gotoCustomQueryViewButton =
            new Button("Custom Queries", e -> parent.switchToCustomQueryView());

    public SideBarLayout(final QueryView parent, MainPresenter mainPresenter) {
        this.parent = parent;
        gotoQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        gotoCustomQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        VerticalLayout queryOptions =
                new VerticalLayout(gotoQueryViewButton, gotoCustomQueryViewButton);

        Button returnToRuleEditorButton =
                new Button(
                        "Return to rule editor", click -> mainPresenter.showArchitectureRuleView());
        add(queryOptions, returnToRuleEditorButton);
        setHeightFull();
        queryOptions.setSizeUndefined();
        returnToRuleEditorButton.setSizeUndefined();
        setFlexGrow(1, queryOptions);
        setFlexGrow(0, returnToRuleEditorButton);
        getStyle().set("background-color", "#3458eb");
    }
}
