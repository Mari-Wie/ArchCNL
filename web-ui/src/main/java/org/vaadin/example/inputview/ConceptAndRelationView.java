package org.vaadin.example.inputview;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConceptAndRelationView extends VerticalLayout {
    CreateNewLayout createNewConceptLayout = new CreateNewLayout("Concepts", "Create new concept");
    CreateNewLayout createNewRelationLayout =
            new CreateNewLayout("Relations", "Create new relation");
    HorizontalLayout bottomBarLayout = new HorizontalLayout();

    public void setUpBottomBar() {
        Button saveButton = new Button("Save");
        Button checkButton = new Button("Check");
        bottomBarLayout.add(saveButton, checkButton);
    }

    public ConceptAndRelationView() {
        createNewConceptLayout.setHeight(50, Unit.PERCENTAGE);
        createNewRelationLayout.setHeight(50, Unit.PERCENTAGE);

        add(createNewConceptLayout);
        add(createNewRelationLayout);
        add(bottomBarLayout);
        getStyle().set("border", "1px solid black");
    }
}
