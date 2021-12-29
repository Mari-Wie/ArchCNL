package org.archcnl.ui.output.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.ui.output.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.output.events.ResultUpdateEvent;

public class FreeTextQueryUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    private HorizontalLayout buttonBar;
    private Button clearButton = new Button("Clear", e -> queryTextArea.clear());
    private Button defaultQueryButton =
            new Button("Default Query", e -> queryTextArea.setValue(exampleQuery));
    private Button importCustomQueryButton =
            new Button(
                    "Use Custom Query",
                    e -> fireEvent(new CustomQueryInsertionRequestedEvent(this, false)));
    private Button applyButton =
            new Button("Apply", e -> fireEvent(new ResultUpdateEvent(this, false)));

    public FreeTextQueryUiComponent() throws PropertyNotFoundException {
        registerEventListeners();
        buttonBar =
                new HorizontalLayout(
                        clearButton, defaultQueryButton, importCustomQueryButton, applyButton);
        addComponents();
    }

    protected void addComponents() {
        add(queryTextArea, buttonBar, gridView);
    }
}
