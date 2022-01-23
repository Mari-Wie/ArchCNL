package org.archcnl.ui.outputview.queryviews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.ui.outputview.queryviews.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.FreeTextRunButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinQueryButtonPressedEvent;

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
    private HorizontalLayout topRow;

    public FreeTextQueryUiComponent() {
        Label caption = new Label("Create a free text query");
        topRow =
                new HorizontalLayout(
                        caption,
                        new Button(
                                new Icon(VaadinIcon.PIN),
                                click -> fireEvent(new PinQueryButtonPressedEvent(this, true))));
        topRow.setWidthFull();
        caption.setWidthFull();
        defaultQueryButton =
                new Button(
                        "Default Query",
                        e -> queryTextArea.setValue(QueryUtils.getDefaultQuery().transformToGui()));
        buttonBar =
                new HorizontalLayout(
                        clearButton, defaultQueryButton, importCustomQueryButton, runButton);
        addComponents();
    }

    protected void addComponents() {
        add(topRow, queryTextArea, buttonBar, gridView);
    }
}
