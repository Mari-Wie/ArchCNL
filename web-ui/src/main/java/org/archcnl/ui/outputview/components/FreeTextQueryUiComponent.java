package org.archcnl.ui.outputview.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.ui.outputview.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.outputview.events.FreeTextRunButtonPressedEvent;

public class FreeTextQueryUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    private HorizontalLayout buttonBar;
    private Button clearButton = new Button("Clear", e -> queryTextArea.clear());
    private Button defaultQueryButton;
    private Button importCustomQueryButton =
            new Button(
                    "Use Custom Query",
                    e -> fireEvent(new CustomQueryInsertionRequestedEvent(this, false)));
    private Button runButton =
            new Button(
                    "Run",
                    e -> fireEvent(new FreeTextRunButtonPressedEvent(gridView, true, getQuery())));

    public FreeTextQueryUiComponent() throws PropertyNotFoundException {
        String defaultQuery = "";
        defaultQuery = QueryUtils.getDefaultQuery().transformToGui();
        final String query = defaultQuery; // The compiler complains without this line
        defaultQueryButton = new Button("Default Query", e -> queryTextArea.setValue(query));
        buttonBar =
                new HorizontalLayout(
                        clearButton, defaultQueryButton, importCustomQueryButton, runButton);
        addComponents();
    }

    protected void addComponents() {
        add(queryTextArea, buttonBar, gridView);
    }
}
