package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.domain.common.BooleanValue;

import com.vaadin.flow.component.combobox.ComboBox;

public class BooleanSelectionView extends ComboBox<String> {

    private static final long serialVersionUID = -2542077868183389974L;

    public BooleanSelectionView() {
        setLabel("Object");
        String[] items = {"True", "False"};
        setItems(items);
        setValue("False");
    }

    public BooleanValue getObject() {
        return new BooleanValue(Boolean.parseBoolean(getValue()));
    }
}
