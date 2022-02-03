package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.textfield.TextField;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;

public class StringSelectionComponent extends TextField {

    private static final long serialVersionUID = -3205839871923884627L;

    public StringSelectionComponent() {
        setPlaceholder("String");
    }

    public StringValue getObject() {
        return new StringValue(getValue());
    }
}
