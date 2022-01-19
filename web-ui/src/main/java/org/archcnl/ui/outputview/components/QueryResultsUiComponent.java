package org.archcnl.ui.outputview.components;

import org.archcnl.application.exceptions.PropertyNotFoundException;

public class QueryResultsUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    private GeneralInfoLayout generalInfoLayout;

    public QueryResultsUiComponent(GridView gridView) throws PropertyNotFoundException {
        this.gridView = gridView;
        generalInfoLayout = new GeneralInfoLayout();
        queryTextArea.setReadOnly(true);
        addComponents();
    }

    protected void addComponents() {
        add(generalInfoLayout, gridView, queryTextArea);
    }
}
