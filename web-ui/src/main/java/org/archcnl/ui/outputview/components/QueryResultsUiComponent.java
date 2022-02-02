package org.archcnl.ui.outputview.components;

import java.util.Optional;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;

public class QueryResultsUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    private GeneralInfoLayout generalInfoLayout;

    public QueryResultsUiComponent() {
        gridView = new GridView();
        generalInfoLayout = new GeneralInfoLayout();
        queryTextArea.setClassName("query-text-box");
        queryTextArea.setReadOnly(true);
        addComponents();
    }

    protected void addComponents() {
        add(generalInfoLayout, gridView, queryTextArea);
    }

    public void updateGridView(final Optional<Result> result) {
        gridView.update(result);
    }
}
