package org.archcnl.ui.output.component;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class FreeTextQueryUiComponent extends AbstractQueryResults {

    private static final long serialVersionUID = 1L;

    HorizontalLayout buttonBar;

    public FreeTextQueryUiComponent() {
        registerEventListeners();
        addComponents();
    }

    protected void addComponents() {
        add(queryTextArea, gridView);
    }
    
    private void importCustomQueryText() {
    	
    }
}
