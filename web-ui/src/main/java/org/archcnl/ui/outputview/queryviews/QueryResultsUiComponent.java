package org.archcnl.ui.outputview.queryviews;

import java.util.Optional;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.ui.outputview.queryviews.components.GeneralInfoLayout;

public class QueryResultsUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    private GeneralInfoLayout generalInfoLayout;

    public QueryResultsUiComponent(String defaultQueryText) {
        super(defaultQueryText);
        generalInfoLayout = new GeneralInfoLayout();
        queryTextArea.setClassName("query-text-box");
        queryTextArea.setReadOnly(true);
        addComponents();
    }

    public void updateGridView(final Optional<Result> result) {
        gridView.update(result);
    }

    protected void addComponents() {
        add(generalInfoLayout, gridView, queryTextArea);
    }
}
