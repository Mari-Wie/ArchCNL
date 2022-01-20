package org.archcnl.ui.common.conceptandrelationlistview.mappinglistlayout;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CreateNewLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public CreateNewLayout(
            final String labelText,
            final String buttonText,
            final ComponentEventListener<ClickEvent<Button>> clickListener,
            final boolean inputSide) {
        final Label archRulesLabel = new Label(labelText);
        final Button createNewRuleButton = new Button(buttonText, clickListener);
        HorizontalLayout buttonRow = new HorizontalLayout(archRulesLabel);
        if (inputSide) {
            buttonRow.add(createNewRuleButton);
        }
        add(buttonRow);
        getStyle().set("border", "1px solid black");
    }
}
