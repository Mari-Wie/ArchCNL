package org.archcnl.ui.input;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConceptAndRelationView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    CreateNewLayout createNewConceptLayout = new CreateNewLayout("Concepts", "Create new concept");
    CreateNewLayout createNewRelationLayout =
            new CreateNewLayout("Relations", "Create new relation");
    HorizontalLayout bottomBarLayout = new HorizontalLayout();

    public void setUpBottomBar() {
        final Button saveButton = new Button("Save");
        final Button checkButton = new Button("Check");
        bottomBarLayout.add(saveButton, checkButton);
    }

    public ConceptAndRelationView() {
        createNewConceptLayout.setHeight(50, Unit.PERCENTAGE);
        createNewRelationLayout.setHeight(50, Unit.PERCENTAGE);

        setUpBottomBar();

        add(createNewConceptLayout);
        add(createNewRelationLayout);
        add(bottomBarLayout);
        getStyle().set("border", "1px solid black");
    }
}
