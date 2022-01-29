package org.archcnl.ui.outputview.components;

public class QueryResultsUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    private GeneralInfoLayout generalInfoLayout;

    public QueryResultsUiComponent(final GridView gridView) {
        this.gridView = gridView;
        generalInfoLayout = new GeneralInfoLayout();
        queryTextArea.setClassName("query-text-box");
        queryTextArea.setReadOnly(true);
        addComponents();
    }

    protected void addComponents() {
        add(generalInfoLayout, gridView, queryTextArea);
    }
}
