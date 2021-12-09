package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.domain.common.StringValue;

public class StringSelectionComponent extends TextField {

    private static final long serialVersionUID = -3205839871923884627L;

    public StringSelectionComponent() {
        setLabel("Object");
        setPlaceholder("String");
    }

    public StringValue getObject() {
        return new StringValue(getValue());
    }
}
