package org.archcnl.ui.output.component;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import java.util.Arrays;
import java.util.List;
import org.archcnl.ui.common.AddRemoveButtonLayout;
import org.archcnl.ui.output.events.AddWhereLayoutRequestEvent;
import org.archcnl.ui.output.events.RemoveWhereLayoutRequestEvent;

public class WhereTextBoxesLayout extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    private TextField subjectTextField = new TextField();
    private TextField objectTextField = new TextField();
    private TextField predicateTextField = new TextField();
    private AddRemoveButtonLayout addRemoveButtonLayout;
    private PlayPauseButton pauseButton = new PlayPauseButton(new Icon(VaadinIcon.PAUSE));
    private boolean isEnabled = true;
    private String tooltipMessageEnabled = "Disables this row of subject, predicate, and object";
    private String tooltipMessageDisabled = "Enables this row of subject, predicate, and object)";

    public WhereTextBoxesLayout() {
        addTextField(
                "Subject",
                subjectTextField,
                e -> {
                    // TODO do something useful with this
                });
        addTextField(
                "Object",
                objectTextField,
                e -> {
                    // TODO do something useful with this
                });
        addTextField(
                "Predicate",
                predicateTextField,
                e -> {
                    // TODO do something useful with this
                });

        pauseButton.addClickListener(
                e -> {
                    pauseRow();
                });
        pauseButton.getElement().setProperty("title", tooltipMessageEnabled);

        addRemoveButtonLayout = new AddRemoveButtonLayout();
        addRemoveButtonLayout.addListener(
                AddWhereLayoutRequestEvent.class,
                e -> fireEvent(new AddWhereLayoutRequestEvent(this, false)));
        addRemoveButtonLayout.addListener(
                RemoveWhereLayoutRequestEvent.class,
                e -> fireEvent(new RemoveWhereLayoutRequestEvent(this, false)));
        add(addRemoveButtonLayout);

        add(pauseButton);
    }

    void pauseRow() {
        isEnabled = !isEnabled;
        subjectTextField.setEnabled(isEnabled);
        objectTextField.setEnabled(isEnabled);
        predicateTextField.setEnabled(isEnabled);
        addRemoveButtonLayout.setEnabled(isEnabled);
        if (isEnabled) {
            pauseButton.getElement().setProperty("title", tooltipMessageEnabled);
        } else {
            pauseButton.getElement().setProperty("title", tooltipMessageDisabled);
        }
    }

    void addTextField(
            final String placeHolder,
            final TextField textField,
            final HasValue.ValueChangeListener<
                            ? super AbstractField.ComponentValueChangeEvent<TextField, String>>
                    listener) {
        textField.setPlaceholder(placeHolder);
        textField.addValueChangeListener(listener);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        add(textField);
    }

    public List<String> getObjSubPredString() {
        return Arrays.asList(
                subjectTextField.getValue(),
                objectTextField.getValue(),
                predicateTextField.getValue());
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
