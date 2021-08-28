package org.archcnl.ui.inputview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CreateNewLayout extends VerticalLayout {
    public CreateNewLayout(String labelText, String buttonText) {
        Label archRulesLabel = new Label(labelText);
        Button createNewRuleButton = new Button(buttonText);
        add(new HorizontalLayout(archRulesLabel, createNewRuleButton));
        getStyle().set("border", "1px solid black");
    }
}
