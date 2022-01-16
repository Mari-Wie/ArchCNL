package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets;

import com.vaadin.flow.component.combobox.ComboBox;
import org.archcnl.domain.common.BooleanValue;

public class BooleanSelectionComponent extends ComboBox<String> {

    private static final long serialVersionUID = -2542077868183389974L;

    public BooleanSelectionComponent() {
        String[] items = {"True", "False"};
        setItems(items);
        setValue("False");
    }

    public BooleanValue getObject() {
        return new BooleanValue(Boolean.parseBoolean(getValue()));
    }
}
