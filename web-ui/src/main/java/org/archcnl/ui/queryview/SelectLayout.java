package org.archcnl.ui.queryview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class SelectLayout extends VerticalLayout {
    private TextField selectTextField = new TextField();
    private String selectString = "";

    SelectLayout() {

        setAlignItems(Alignment.START);
        selectTextField.setPlaceholder("selectField");
        selectTextField.addValueChangeListener(e -> updateSelect());
        selectTextField.setValueChangeMode(ValueChangeMode.LAZY);
        add(selectTextField);
    }

    void updateSelect() {
        selectString = selectTextField.getValue();
    }

    String getValue() {
        return selectString;
    }
}
