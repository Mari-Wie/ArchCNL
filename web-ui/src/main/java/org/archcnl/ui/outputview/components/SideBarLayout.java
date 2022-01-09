package org.archcnl.ui.outputview.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.archcnl.ui.MainPresenter;

public class SideBarLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    QueryView parent;
    Button gotoQueryViewButton = new Button("General Information", e -> parent.switchToQueryView());
    Button gotoCustomQueryViewButton =
            new Button("Custom Queries", e -> parent.switchToCustomQueryView());
    Button gotoFreeTextQueryViewButton =
            new Button("Free Text Queries", e -> parent.switchToFreeTextQueryView());

    public SideBarLayout(final QueryView parent, MainPresenter mainPresenter) {
        this.parent = parent;
        gotoQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        gotoCustomQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        gotoFreeTextQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        VerticalLayout queryOptions =
                new VerticalLayout(
                        gotoQueryViewButton,
                        gotoCustomQueryViewButton,
                        gotoFreeTextQueryViewButton);

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
