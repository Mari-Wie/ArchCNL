package org.archcnl.ui.outputview.queryviews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.ui.outputview.queryviews.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.DeleteButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.FreeTextRunButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinFreeTextQueryRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.QueryNameUpdateRequestedEvent;

public class FreeTextQueryComponent extends AbstractQueryComponent {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_NAME = "Pinned Full Text Query";

    private HorizontalLayout buttonBar;
    private Button clearButton = new Button("Clear", e -> queryTextArea.clear());
    private Button defaultQueryButton;
    private TextField queryName;
    private Button pinButton;
    private Button deleteButton;
    private Button importCustomQueryButton =
            new Button(
                    "Use Custom Query",
                    e -> fireEvent(new CustomQueryInsertionRequestedEvent(this, false)));
    private Button runButton =
            new Button(
                    "Run",
                    e -> fireEvent(new FreeTextRunButtonPressedEvent(gridView, true, getQuery())));
    private HorizontalLayout topRow;

    public FreeTextQueryComponent(String defaultQueryText) {
        super(defaultQueryText);
        Label caption = new Label("Create a free text query");
        caption.setWidthFull();

        pinButton =
                new Button(
                        new Icon(VaadinIcon.PIN),
                        click -> {
                            replacePinButtonWithDeleteButton();
                            fireEvent(
                                    new PinFreeTextQueryRequestedEvent(this, true, getQueryName()));
                        });
        deleteButton =
                new Button(
                        new Icon(VaadinIcon.TRASH),
                        click -> fireEvent(new DeleteButtonPressedEvent(this, true)));
        topRow = new HorizontalLayout(caption, pinButton);
        topRow.setWidthFull();

        queryName = new TextField("Name");
        queryName.setPlaceholder("Name of this query");
        queryName.addValueChangeListener(event -> fireNameUpdateEventIfNameNotEmpty());

        defaultQueryButton =
                new Button(
                        "Default Query", e -> queryTextArea.setValue(QueryUtils.getDefaultQuery()));
        buttonBar =
                new HorizontalLayout(
                        clearButton, defaultQueryButton, importCustomQueryButton, runButton);
        addComponents();
    }

    protected void addComponents() {
        add(topRow, queryName, queryTextArea, buttonBar, gridView);
    }

    public FreeTextQuery makeQuery() {
        return new FreeTextQuery(getQueryName(), getQuery());
    }

    private String getQueryName() {
        return queryName.getOptionalValue().orElse(DEFAULT_NAME);
    }

    public void replacePinButtonWithDeleteButton() {
        topRow.replace(pinButton, deleteButton);
    }

    public void setQueryName(String name) {
        queryName.setValue(name);
    }

    private void fireNameUpdateEventIfNameNotEmpty() {
        if (queryName.getOptionalValue().isPresent()) {
            fireEvent(new QueryNameUpdateRequestedEvent(this, true, queryName.getValue()));
        }
    }
}
