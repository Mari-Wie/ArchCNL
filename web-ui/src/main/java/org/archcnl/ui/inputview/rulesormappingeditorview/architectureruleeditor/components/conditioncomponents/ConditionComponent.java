package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.conditioncomponents;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.Arrays;
import java.util.List;
import org.archcnl.ui.common.andtriplets.triplet.PredicateSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.textfieldwidgets.ConceptTextfieldWidget;

public class ConditionComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private Label startLabelTextfield, endLabelTextfield;
    private PredicateSelectionComponent firstVariable;
    private ConceptTextfieldWidget secondVariable;
    private ComboBox<String> firstCombobox;
    private Checkbox andCheckbox;
    private ConditionComponent newCondition;

    public ConditionComponent() {
        setMargin(false);
        setPadding(false);
        HorizontalLayout conditionBox = new HorizontalLayout();
        conditionBox.setMargin(false);

        startLabelTextfield = new Label("that(");
        conditionBox.setVerticalComponentAlignment(Alignment.END, startLabelTextfield);

        List<String> firstStatements =
                Arrays.asList(" ", "a", "an", "equal-to", "equal-to a", "equal-to an");
        firstCombobox = new ComboBox<String>("Modifier", firstStatements);
        firstCombobox.setValue("a");
        firstCombobox.addValueChangeListener(
                e -> {
                    firstComboboxListener(firstCombobox.getValue());
                });

        createFirstVariable();
        createSecondVariable();

        endLabelTextfield = new Label(")");
        conditionBox.setVerticalComponentAlignment(Alignment.END, endLabelTextfield);

        andCheckbox = new Checkbox("and...");
        conditionBox.setVerticalComponentAlignment(Alignment.END, andCheckbox);
        andCheckbox.addClickListener(e -> addCondition(andCheckbox.getValue()));

        conditionBox.add(
                startLabelTextfield,
                firstVariable,
                firstCombobox,
                secondVariable,
                endLabelTextfield,
                andCheckbox);
        add(conditionBox);
    }

    private void createSecondVariable() {
        secondVariable = new ConceptTextfieldWidget();
        secondVariable.setLabel("Concept");
        secondVariable.setPlaceholder("Concept");
    }

    private void createFirstVariable() {
        firstVariable = new PredicateSelectionComponent();
        firstVariable.setLabel("Relation");
        firstVariable.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
    }

    private void firstComboboxListener(String value) {
        secondVariable.setLabel("Concept / Number / String");
        secondVariable.setPlaceholder("+/- [0-9] / String");
        if (value.equals("a") || value.equals("an")) {
            secondVariable.setLabel("Concept");
            secondVariable.setPlaceholder("Concept");
        }
    }

    private void addCondition(Boolean showCondition) {
        if (newCondition == null) {
            newCondition = new ConditionComponent();
        }
        if (showCondition) {
            add(newCondition);
        } else {
            remove(newCondition);
        }
    }

    @Override
    public String getRuleString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(startLabelTextfield.getText() + "");
        sBuilder.append(firstVariable.getValue() + " ");
        sBuilder.append(firstCombobox.getValue() + " ");
        sBuilder.append(secondVariable.getValue());
        sBuilder.append(endLabelTextfield.getText() + " ");
        if (andCheckbox.getValue()) {
            sBuilder.append("and " + newCondition.getRuleString());
        }

        return sBuilder.toString();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
