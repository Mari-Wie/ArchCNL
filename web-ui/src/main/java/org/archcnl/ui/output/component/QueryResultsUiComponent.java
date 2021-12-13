package org.archcnl.ui.output.component;

public class QueryResultsUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    GeneralInfoLayout generalInfoLayout;

    public QueryResultsUiComponent() {
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
