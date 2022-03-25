package org.archcnl.ui.outputview.queryviews;

import com.vaadin.flow.component.html.Label;
import java.util.Optional;
import org.archcnl.domain.output.model.query.PredefinedQuery;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;

public class PredefinedQueryComponent extends AbstractQueryComponent {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final Label description;

    public PredefinedQueryComponent(PredefinedQuery query) {
        super(query.getQueryString());
        queryTextArea.setReadOnly(true);
        this.name = query.getName();
        description = new Label(query.getDescription());
        addComponents();
    }

    public void updateGridView(final Optional<Result> result) {
        gridView.update(result);
    }

    public String getName() {
        return name;
    }

    protected void addComponents() {
        add(description, gridView, queryTextArea);
    }
}
