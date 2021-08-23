package org.vaadin.example;

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

    WhereTextBoxesLayout() {
        addTextField(
                "Subject",
                subjectTextField,
                e -> {
                    // TODO do something useful with this
                    System.out.println(subjectTextField.getValue());
                });
        addTextField(
                "Object",
                objectTextField,
                e -> {
                    // TODO do something useful with this
                    System.out.println(objectTextField.getValue());
                });
        addTextField(
                "Predicate",
                predicateTextField,
                e -> {
                    // TODO do something useful with this
                    System.out.println(predicateTextField.getValue());
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
                    isEnabled = !isEnabled;
                });
        add(addButton);
        add(minusButton);
        add(pauseButton);
    }

    void addTextField(
            String placeHolder,
            TextField textField,
            HasValue.ValueChangeListener<
                            ? super AbstractField.ComponentValueChangeEvent<TextField, String>>
                    listener) {
        textField.setPlaceholder(placeHolder);
        textField.addValueChangeListener(listener);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        add(textField);
    }

    public List<String> getObjSubPraedString() {
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
