package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.combobox.ComboBox;
import org.archcnl.domain.input.model.mappings.BooleanValue;

public class BooleanSelectionView extends ComboBox<String> {

    private static final long serialVersionUID = -2542077868183389974L;

    public BooleanSelectionView() {
        setLabel("Object");
        setPlaceholder("Boolean");
        String[] items = {"True", "False"};
        setItems(items);
        setValue("False");
    }

    public BooleanValue getObject() {
        return new BooleanValue(Boolean.parseBoolean(getValue()));
    }
}
