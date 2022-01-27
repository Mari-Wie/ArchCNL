package org.archcnl.ui.input.ruleeditor.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.util.Arrays;
import java.util.List;

public class ConditionComponent extends RuleComponent {

    private static final long serialVersionUID = 1L;
    private Label startLabelTextfield, endLabelTextfield;
    private RelationTextfieldComponent firstVariable;
    private ConceptTextfieldComponent secondVariable;
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
        firstVariable = new RelationTextfieldComponent();
        firstVariable.setLabel("Relation");

        List<String> firstStatements =
                Arrays.asList(" ", "a", "an", "equal-to", "equal-to a", "equal-to an");
        firstCombobox = new ComboBox<String>("Modifier", firstStatements);
        firstCombobox.setValue("a");
        firstCombobox.addValueChangeListener(
                e -> {
                    firstComboboxListener(firstCombobox.getValue());
                });
        
        secondVariable = new ConceptTextfieldComponent();
        secondVariable.setLabel("Concept");
        secondVariable.setPlaceholder("Concept");
        
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
    
    private void firstComboboxListener(String value) {
        secondVariable.setLabel("Concept / Number / String");
        secondVariable.setPlaceholder("+/- [0-9] / String");
        if(value.equals("a") || value.equals("an"))
        {
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

    public String getString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(startLabelTextfield.getText() + "");
        sBuilder.append(firstVariable.getValue() + " ");
        sBuilder.append(firstCombobox.getValue() + " ");
        sBuilder.append(secondVariable.getValue());
        sBuilder.append(endLabelTextfield.getText() + " ");
        if (andCheckbox.getValue()) {
            sBuilder.append("and " + newCondition.getString());
        }

        return sBuilder.toString();
    }
}
