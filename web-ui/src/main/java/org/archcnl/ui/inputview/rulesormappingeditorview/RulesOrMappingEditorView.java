package org.archcnl.ui.inputview.rulesormappingeditorview;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class RulesOrMappingEditorView extends VerticalLayout {

    private static final long serialVersionUID = -8185825107846587765L;
    protected HorizontalLayout footer = new HorizontalLayout();
    protected Component addNodeElement;
    protected Button addNode = new Button("No LABEL");

    public RulesOrMappingEditorView() {
        footer = new HorizontalLayout();
    }

    public HorizontalLayout createCreateNewLayout(
            String buttonLabel, ComponentEventListener<ClickEvent<Button>> clickListener) {

        addNode = new Button(buttonLabel, clickListener);
        addNodeElement = addNode;
        footer.add(addNodeElement);
        return footer;
    }
}
