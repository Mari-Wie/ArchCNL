package org.archcnl.ui.outputview.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class SelectLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private TextField selectTextField;
    private String selectString;

    SelectLayout() {
        selectTextField = new TextField();
        selectString = "";
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
