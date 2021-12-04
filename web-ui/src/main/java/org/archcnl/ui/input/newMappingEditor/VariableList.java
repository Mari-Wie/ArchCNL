package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.util.List;
import java.util.stream.Collectors;

public class VariableList extends HorizontalLayout {
    public VariableList() {
        setPadding(false);
        getStyle().set("border", "1px solid black");
        add(new Label("Variables"));
    }

    public List<String> collect() {
        return getChildren()
                .filter(child -> child.getClass() == Span.class)
                .map(Span.class::cast)
                .map(Span::getText)
                .collect(Collectors.toList());
    }
    public void addVariable(String variable) {
            // TODO: Fix setup of Spans, general css layout could be at fault
            Span nTextField = new Span(variable);
            add(nTextField);
    }
}
