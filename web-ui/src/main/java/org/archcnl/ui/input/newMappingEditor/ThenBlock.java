package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.input.newMappingEditor.events.VariableListUpdateRequest;

public class ThenBlock extends HorizontalLayout {

    /** */
    private static final long serialVersionUID = 1L;

    ComboBox<String> subjectComboBox;
    TextField predicateTextField;
    TextField objectTextField;

    public ThenBlock() {
        subjectComboBox = new ComboBox<String>();
        subjectComboBox.addFocusListener(
                e -> fireEvent(new VariableListUpdateRequest(subjectComboBox, false)));
        add(subjectComboBox);

        predicateTextField = new TextField();
        predicateTextField.setReadOnly(true);
        add(predicateTextField);

        objectTextField = new TextField();
        objectTextField.setReadOnly(true);
        add(objectTextField);
    }

    public void setPredicate(String pred) {
        predicateTextField.setValue(pred);
    }

    public void setObject(String obj) {
        objectTextField.setValue(obj);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
