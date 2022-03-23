package org.archcnl.ui.outputview.queryviews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import org.archcnl.domain.output.model.query.PrespecifiedQuery;
import org.archcnl.ui.outputview.queryviews.events.FreeTextRunButtonPressedEvent;

public class PrespecifiedQueryComponent extends AbstractQueryComponent {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final Label description;

    private Button runButton =
            new Button(
                    "Run",
                    e -> fireEvent(new FreeTextRunButtonPressedEvent(gridView, true, getQuery())));

    public PrespecifiedQueryComponent(PrespecifiedQuery query) {
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
