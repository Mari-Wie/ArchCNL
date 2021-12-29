package org.archcnl.ui.output.component;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.ui.output.events.ResultUpdateEvent;

public class CustomQueryUiComponent extends AbstractQueryResultsComponent {

    private static final long serialVersionUID = 1L;

    private WhereLayout whereLayout = new WhereLayout();
    private SelectLayout selectLayout = new SelectLayout();
    private Label selectLabel = new Label("Select");
    private Label whereLabel = new Label("Where");
    private HorizontalLayout selectLabelLayout = new HorizontalLayout(selectLabel);
    private HorizontalLayout whereLabelLayout =
            new HorizontalLayout(whereLabel, new HideShowButton(whereLayout));
    // private Button queryButton = new Button("Apply", e -> updateGrid());
    private Button queryButton =
            new Button("Apply", e -> fireEvent(new ResultUpdateEvent(this, false)));
    private Button clearButton = new Button("Clear", e -> whereLayout.clear());
    private HideShowButton hideButton = new HideShowButton(gridView);
    private HideShowButton hideQueryTextArea = new HideShowButton(queryTextArea);

    public CustomQueryUiComponent() throws PropertyNotFoundException {
        whereLabel.setHeight(100, Unit.PERCENTAGE);
        queryTextArea.setReadOnly(true);
        addComponents();
    }

    protected void addComponents() {
        add(
                selectLabelLayout,
                selectLayout,
                whereLabelLayout,
                whereLayout,
                new HorizontalLayout(queryButton, clearButton, hideButton),
                gridView,
                hideQueryTextArea,
                queryTextArea);
    }
}
