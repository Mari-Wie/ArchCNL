package org.archcnl.ui.output.component;

import org.archcnl.application.exceptions.PropertyNotFoundException;

public class QueryResultsUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    GeneralInfoLayout generalInfoLayout;

    public QueryResultsUiComponent() throws PropertyNotFoundException {
        generalInfoLayout = new GeneralInfoLayout();
        gridView.update(exampleQuery);
        queryTextArea.setReadOnly(true);
        registerEventListeners();
        addComponents();
    }

    protected void addComponents() {
        add(generalInfoLayout, gridView, queryTextArea);
    }
}
