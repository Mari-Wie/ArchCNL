package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class VariableList extends HorizontalLayout {
    public VariableList() {
        setPadding(false);
        getStyle().set("border", "1px solid black");
        add(new Label("Variables"));
    }
}
