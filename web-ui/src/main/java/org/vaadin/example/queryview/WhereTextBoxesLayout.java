package org.vaadin.example.queryview;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import java.util.Arrays;
import java.util.List;

public class WhereTextBoxesLayout extends HorizontalLayout {
    private TextField subjectTextField = new TextField();
    private TextField objectTextField = new TextField();
    private TextField predicateTextField = new TextField();
    private Button addButton = new Button(new Icon(VaadinIcon.PLUS));
    private Button minusButton = new Button(new Icon(VaadinIcon.MINUS));
    private PauseButton pauseButton = new PauseButton(new Icon(VaadinIcon.PAUSE));
    private boolean isEnabled = true;
    private boolean isLast = false;
    private TextField subjectTextfield, objectTextfield, predicateTextfield;

    public WhereTextBoxesLayout() {
        subjectTextfield =
                addTextField(
                        "Subject",
                        subjectTextField,
                        e -> {
                            // TODO do something useful with this
                        });
        objectTextfield =
                addTextField(
                        "Object",
                        objectTextField,
                        e -> {
                            // TODO do something useful with this
                        });
        predicateTextfield =
                addTextField(
                        "Predicate",
                        predicateTextField,
                        e -> {
                            // TODO do something useful with this
                        });

        addButton.addClickListener(
                e -> {
                    fireEvent(new AddWhereLayoutRequestEvent(this, false));
                });
        minusButton.addClickListener(
                e -> {
                    fireEvent(new RemoveWhereLayoutRequestEvent(this, false));
                });
        pauseButton.addClickListener(
                e -> {
                    pauseRow();
                });
        pauseButton.getElement().setProperty("title", "Pauses (Disables) this row of queries");
        add(addButton);
        add(minusButton);
        add(pauseButton);
    }

    void pauseRow() {
        isEnabled = !isEnabled;
        subjectTextfield.setEnabled(isEnabled);
        objectTextfield.setEnabled(isEnabled);
        predicateTextfield.setEnabled(isEnabled);
        if (isEnabled) {
            pauseButton.getElement().setProperty("title", "Pauses (Disables) this row of queries");
        } else {
            pauseButton.getElement().setProperty("title", "Unpauses this row of queries");
        }
    }

    TextField addTextField(
            String placeHolder,
            TextField textField,
            HasValue.ValueChangeListener<
                            ? super AbstractField.ComponentValueChangeEvent<TextField, String>>
                    listener) {
        textField.setPlaceholder(placeHolder);
        textField.addValueChangeListener(listener);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        add(textField);
        return textField;
    }

    public List<String> getObjSubPredString() {
        return Arrays.asList(
                subjectTextField.getValue(),
                objectTextField.getValue(),
                predicateTextField.getValue());
    }

    public <T extends ComponentEvent<?>> Registration addListener(
            Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
