package org.archcnl.ui.input.ruleeditor.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Arrays;
import java.util.List;

public class ConditionComponent extends RuleComponent {

    private static final long serialVersionUID = 1L;
    private Label startLabelTextfield, endLabelTextfield;
    private TextField firstRelation, secondConcept;
    private ComboBox<String> firstCombobox;
    private Checkbox andCheckbox;
    private ConditionComponent newCondition;

    public ConditionComponent() {
        HorizontalLayout conditionBox = new HorizontalLayout();
        startLabelTextfield = new Label("that (");
        conditionBox.setVerticalComponentAlignment(Alignment.END, startLabelTextfield);
        firstRelation = new TextField("Relation");

        List<String> firstStatements =
                Arrays.asList(" ", "a", "an", "equal-to", "equal-to a", "equal-to an");
        firstCombobox = new ComboBox<String>("Modifier", firstStatements);
        firstCombobox.setValue("a");
        secondConcept = new TextField("Concept");
        endLabelTextfield = new Label(")");
        conditionBox.setVerticalComponentAlignment(Alignment.END, endLabelTextfield);

        andCheckbox = new Checkbox("and...");
        conditionBox.setVerticalComponentAlignment(Alignment.END, andCheckbox);
        andCheckbox.addClickListener(e -> addCondition(andCheckbox.getValue()));

        conditionBox.add(
                startLabelTextfield,
                firstRelation,
                firstCombobox,
                secondConcept,
                endLabelTextfield,
                andCheckbox);
        add(conditionBox);
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
        sBuilder.append(startLabelTextfield.getText() + " ");
        sBuilder.append(firstRelation.getValue() + " ");
        sBuilder.append(firstCombobox.getValue() + " ");
        sBuilder.append(secondConcept.getValue() + " ");
        sBuilder.append(endLabelTextfield.getText() + " ");
        if (andCheckbox.getValue()) {
            sBuilder.append("and " + newCondition.getString());
        }

        return sBuilder.toString();
    }
}
