package org.archcnl.ui.outputview.queryviews;

import org.archcnl.ui.outputview.queryviews.components.GeneralInfoLayout;
import org.archcnl.ui.outputview.queryviews.components.GridView;

public class QueryResultsUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    private GeneralInfoLayout generalInfoLayout;

    public QueryResultsUiComponent(GridView gridView) {
        this.gridView = gridView;
        generalInfoLayout = new GeneralInfoLayout();
        queryTextArea.setReadOnly(true);
        addComponents();
    }

    protected void addComponents() {
        add(generalInfoLayout, gridView, queryTextArea);
    }
}
