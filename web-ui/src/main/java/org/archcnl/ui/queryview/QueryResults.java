package org.archcnl.ui.queryview;

public class QueryResults extends AbstractQueryResults {

    GeneralInfoLayout generalInfoLayout = new GeneralInfoLayout();

    public QueryResults() {
        super();
        gridView.update(exampleQuery);
        registerEventListeners();
        addComponents();
    }

    protected void addComponents() {
        add(generalInfoLayout, gridView, queryTextArea);
    }
}
