package org.archcnl.ui.output.component;

import org.archcnl.ui.output.events.ResultUpdateEvent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class FreeTextQueryUiComponent extends AbstractQueryResults {

    private static final long serialVersionUID = 1L;

    private QueryView parent;
    private HorizontalLayout buttonBar;
    private Button clearButton = new Button("Clear", e -> queryTextArea.clear());
    private Button defaultQueryButton = new Button("Default Query", e -> queryTextArea.setValue(exampleQuery));
    private Button importCustomQueryButton = new Button("Import Custom Query", e -> importCustomQueryText());
    private Button applyButton = new Button("Apply", e -> fireEvent(new ResultUpdateEvent(this, false)));

    // TODO change the QueryView that is handed over to a Presenter when refactoring the Output according to MVP
    public FreeTextQueryUiComponent(QueryView queryView) {
    	parent = queryView;
        registerEventListeners();
        buttonBar = new HorizontalLayout(clearButton, defaultQueryButton, importCustomQueryButton, applyButton);
        addComponents();
    }

    protected void addComponents() {
        add(queryTextArea, buttonBar, gridView);
    }
    
    private void importCustomQueryText() {
    	queryTextArea.setValue(parent.getCustomQuery());
    }
}
