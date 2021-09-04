package org.archcnl.ui.common;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.example.queryview.QueryView;

public class SideBarLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    QueryView parent;
    Button gotoQueryViewButton = new Button("General Information", e -> parent.switchToQueryView());
    Button gotoCustomQueryViewButton =
            new Button("Custom Queries", e -> parent.switchToCustomQueryView());

    public SideBarLayout(QueryView parent) {
        this.parent = parent;
        gotoQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        gotoCustomQueryViewButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        add(gotoQueryViewButton, gotoCustomQueryViewButton);
        getStyle().set("background-color", "#3458eb");
    }
}
