package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.Arrays;
import java.util.List;
import org.archcnl.ui.common.AddRemoveButtonLayout;
import org.archcnl.ui.output.events.AddWhereLayoutRequestEvent;
import org.archcnl.ui.output.events.RemoveWhereLayoutRequestEvent;

public class ComplexTriplet extends HorizontalLayout {
    /** */
    private static final long serialVersionUID = 1L;

    ComboBox<String> subjectComboBox;
    ComboBox<String> predicateComboBox;
    ComboBox<String> variableTypeComboBox;
    ComboBox<String> objectComboBox;
    AddRemoveButtonLayout addRemoveButtonLayout;

    public ComplexTriplet() {
        createComboBoxes();
        createAddRemoveButtons();
        updateVariablePossibilities();
        updatePredicatePossibilities();
    }
    
    private void createComboBoxes() {
        subjectComboBox = new ComboBox<String>();
        subjectComboBox.setAllowCustomValue(true);
        subjectComboBox.addCustomValueSetListener(e-> fireEvent(new VariableSelectionEvent(subjectComboBox, false,e.getDetail())));
        add(subjectComboBox);
        predicateComboBox = new ComboBox<String>();
        predicateComboBox.addValueChangeListener(
                e -> fireEvent(new PredicateSelectionEvent(variableTypeComboBox, false)));
        add(predicateComboBox);
        variableTypeComboBox = new ComboBox<String>();
        add(variableTypeComboBox);
        objectComboBox = new ComboBox<String>();
        add(objectComboBox);
    }

    private void createAddRemoveButtons() {
        addRemoveButtonLayout = new AddRemoveButtonLayout();
        addRemoveButtonLayout.addListener(
                AddWhereLayoutRequestEvent.class,
                e -> fireEvent(new AddWhereLayoutRequestEvent<ComplexTriplet>(this, false)));
        addRemoveButtonLayout.addListener(
                RemoveWhereLayoutRequestEvent.class,
                e -> fireEvent(new RemoveWhereLayoutRequestEvent<ComplexTriplet>(this, false)));
        add(addRemoveButtonLayout);
    }

    private void updateVariablePossibilities() {
        List<String> testItems = Arrays.asList("A", "b", "C");
        variableTypeComboBox.setItems(testItems);
    }

    private void updatePredicatePossibilities() {
        List<String> testItems = Arrays.asList("pA", "pb", "pC");
        predicateComboBox.setItems(testItems);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
