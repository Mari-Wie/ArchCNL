package org.vaadin.example.queryview;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class CustomQueryResults extends AbstractQueryResults {

    private WhereLayout whereLayout = new WhereLayout();
    private SelectLayout selectLayout = new SelectLayout();
    private Label selectLabel = new Label("Select");
    private Label whereLabel = new Label("Where");
    private HorizontalLayout selectLabelLayout = new HorizontalLayout(selectLabel);
    private HorizontalLayout whereLabelLayout =
            new HorizontalLayout(whereLabel, new HideButton(whereLayout));
    // private Button queryButton = new Button("Apply", e -> updateGrid());
    private Button queryButton =
            new Button("Apply", e -> fireEvent(new ResultUpdateEvent(this, false)));
    private Button clearButton = new Button("Clear", e -> whereLayout.clear());
    private HideButton hideButton = new HideButton(gridView);
    private HideButton hideQueryTextArea = new HideButton(queryTextArea);

    public CustomQueryResults() {
        super();
        whereLabel.setHeight(100, Unit.PERCENTAGE);
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
