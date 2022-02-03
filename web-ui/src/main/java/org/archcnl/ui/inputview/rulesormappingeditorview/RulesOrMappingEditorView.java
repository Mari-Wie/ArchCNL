package org.archcnl.ui.inputview.rulesormappingeditorview;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class RulesOrMappingEditorView extends VerticalLayout {

    private static final long serialVersionUID = -8185825107846587765L;

    public HorizontalLayout createCreateNewLayout(
            String label,
            String buttonLabel,
            ComponentEventListener<ClickEvent<Button>> clickListener) {
        HorizontalLayout createNewLayout = new HorizontalLayout();

        final Label archRulesLabel = new Label(label);
        final Button createNewRuleButton = new Button(buttonLabel, clickListener);
        createNewLayout.add(archRulesLabel);
        createNewLayout.add(createNewRuleButton);
        createNewLayout.getStyle().remove("border");
        add(createNewLayout);
        return createNewLayout;
    }
}
