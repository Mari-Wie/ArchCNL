package org.archcnl.ui.outputview.queryviews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import org.archcnl.domain.output.model.query.PredefinedQuery;
import org.archcnl.ui.outputview.queryviews.events.RunQueryRequestedEvent;

public class PredefinedQueryComponent extends AbstractQueryComponent {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final Label description;

    private Button runButton =
            new Button(
                    "Run",
                    e -> fireEvent(new RunQueryRequestedEvent(this, true, getQuery(), gridView)));

    public PredefinedQueryComponent(PredefinedQuery query) {
        super(query.getQueryString());
        queryTextArea.setReadOnly(true);
        this.name = query.getName();
        description = new Label(query.getDescription());
        addComponents();
    }

    public String getName() {
        return name;
    }

    protected void addComponents() {
        add(description, queryTextArea, runButton, gridView);
    }
}
