package org.archcnl.ui.input.ruleeditor.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Arrays;
import java.util.List;

public class IfStatementComponent extends RuleComponent {

    private static final long serialVersionUID = 1L;
    private ComboBox<String> firstCombobox, secondCombobox;
    private TextField firstVariable, secondVariable, thirdVariable;
    private HorizontalLayout componentRuleLayout;
    private boolean showSecondVariable = true, showSecondCombobox = true, showThirdVariable;
    private Checkbox conditionCheckbox;
    private ConditionComponent newCondition;

    public IfStatementComponent() {
        this.add(new Label("Rule Statement"));
        this.getStyle().set("border", "1px solid black");

        componentRuleLayout = new HorizontalLayout();
        List<String> secondStatements =
                Arrays.asList("must", "can-only", "can", "must be", "must be a", "must be an");
        firstCombobox = new ComboBox<String>("Modifier", secondStatements);
        firstCombobox.setValue("must");
        firstCombobox.addValueChangeListener(
                e -> {
                    firstComboboxListener(firstCombobox.getValue());
                });

        firstVariable = new TextField("Relation");

        List<String> firstModifier =
                Arrays.asList(
                        " ",
                        "a",
                        "an",
                        "anything",
                        "equal-to",
                        "equal-to anything",
                        "at-most",
                        "at-least",
                        "exactly",
                        "equal-to at-most",
                        "equal-to at-least",
                        "equal-to exactly");
        secondCombobox = new ComboBox<String>("Modifier", firstModifier);
        secondCombobox.setValue("a");
        secondCombobox.addValueChangeListener(
                e -> {
                    secondComboboxListener(secondCombobox.getValue());
                });

        secondVariable = new TextField("Variable");
        thirdVariable = new TextField("Variable");
        conditionCheckbox = new Checkbox("that... (add condition)");
        conditionCheckbox.addClickListener(e -> addCondition(conditionCheckbox.getValue()));
        componentRuleLayout.setVerticalComponentAlignment(Alignment.END, conditionCheckbox);
        newCondition = new ConditionComponent();

        componentRuleLayout.add(firstCombobox, firstVariable, secondCombobox, secondVariable);
        add(componentRuleLayout);
    }

    private void firstComboboxListener(String value) {
        if (value.equals("must be") || value.equals("must be a") || value.equals("must be an")) {
            componentRuleLayout.remove(secondCombobox, secondVariable);
            componentRuleLayout.add(conditionCheckbox);
            showSecondCombobox = false;
            showSecondVariable = false;
        } else {
            componentRuleLayout.add(secondCombobox, secondVariable);
            componentRuleLayout.remove(conditionCheckbox);
            showSecondCombobox = true;
            showSecondVariable = true;
        }
    }

    private void secondComboboxListener(String value) {
        if (value.equals("at-most")
                || value.equals("at-least")
                || value.equals("exactly")
                || value.equals("equal-to at-most")
                || value.equals("equal-to at-least")
                || value.equals("equal-to exactly")) {
            componentRuleLayout.add(secondVariable, thirdVariable, conditionCheckbox);
            secondVariable.setPlaceholder("+/- [0-9]");
            showSecondVariable = true;
            showThirdVariable = true;
        } else if (value.equals("anything") || value.equals("equal-to anything")) {
            componentRuleLayout.remove(secondVariable, thirdVariable, conditionCheckbox);
            showSecondVariable = false;
        } else {
            componentRuleLayout.add(secondVariable);
            componentRuleLayout.remove(thirdVariable, conditionCheckbox);
            secondVariable.setPlaceholder("+/- [0-9]");
            showSecondVariable = true;
            showThirdVariable = false;
        }
    }

    public void addCondition(Boolean showCondition) {
        if (showCondition) {
            add(newCondition);
        } else {
            remove(newCondition);
        }
    }

    public String getString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(firstCombobox.getValue() + " ");
        sBuilder.append(firstVariable.getValue() + " ");
        if (showSecondCombobox) {
            sBuilder.append(secondCombobox.getValue() + " ");
        }
        if (showSecondVariable) {
            sBuilder.append(secondVariable.getValue() + " ");
        }
        if (showThirdVariable) {
            sBuilder.append(thirdVariable.getValue() + " ");
            if (conditionCheckbox.getValue()) {
                sBuilder.append(newCondition.getString() + " ");
            }
        }
        return sBuilder.toString();
    }
}
