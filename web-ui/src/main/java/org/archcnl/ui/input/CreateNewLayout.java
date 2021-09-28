package org.archcnl.ui.input;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CreateNewLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public CreateNewLayout(final String labelText, final String buttonText) {
        final Label archRulesLabel = new Label(labelText);
        final Button createNewRuleButton = new Button(buttonText);
        add(new HorizontalLayout(archRulesLabel, createNewRuleButton));
        getStyle().set("border", "1px solid black");
    }
}