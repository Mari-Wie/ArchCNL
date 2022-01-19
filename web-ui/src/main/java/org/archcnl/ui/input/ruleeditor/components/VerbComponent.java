package org.archcnl.ui.input.ruleeditor.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Arrays;
import java.util.List;

public class VerbComponent extends RuleComponent {

    private static final long serialVersionUID = 1L;
    private ComboBox<String> secondCombobox, thirdCombobox;
    private TextField firstVariable, secondVariable, thirdVariable;
    private HorizontalLayout componentRuleLayout;
    private boolean showSecondConcept, showThirdConcept;
    private Checkbox conditionCheckbox;
    private ConditionComponent newCondition;

    public VerbComponent() {
        this.add(new Label("Rule Statement"));
        this.getStyle().set("border", "1px solid black");
        componentRuleLayout = new HorizontalLayout();
        List<String> secondStatements =
                Arrays.asList("must", "can-only", "can", "must be", "must be a", "must be an");
        secondCombobox = new ComboBox<String>("Modifier", secondStatements);
        secondCombobox.setValue("must");

        firstVariable = new TextField("Relation");
        // firstVariable.setPlaceholder("reside-in");

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
        thirdCombobox = new ComboBox<String>("Modifier", firstModifier);
        thirdCombobox.setValue("a");
        thirdCombobox.addValueChangeListener(
                e -> {
                    thirdComboboxListener(thirdCombobox.getValue());
                });

        secondVariable = new TextField("Variable");
        // secondVariable.setPlaceholder("DomainRing");

        thirdVariable = new TextField("Variable");
        conditionCheckbox = new Checkbox("that... (add condition)");
        conditionCheckbox.addClickListener(e -> addCondition(conditionCheckbox.getValue()));
        componentRuleLayout.setVerticalComponentAlignment(Alignment.END, conditionCheckbox);
        newCondition = new ConditionComponent();

        componentRuleLayout.add(secondCombobox, firstVariable, thirdCombobox, secondVariable);
        add(componentRuleLayout);
    }

    private void thirdComboboxListener(String value) {
        if (value.equals("at-most")
                || value.equals("at-least")
                || value.equals("exactly")
                || value.equals("equal-to at-most")
                || value.equals("equal-to at-least")
                || value.equals("equal-to exactly")) {
            componentRuleLayout.add(secondVariable);
            componentRuleLayout.add(thirdVariable);
            componentRuleLayout.add(conditionCheckbox);
            showSecondConcept = true;
            showThirdConcept = true;
        } else if (value.equals("anything") || value.equals("equal-to anything")) {
            componentRuleLayout.remove(secondVariable);
            componentRuleLayout.remove(thirdVariable);
            componentRuleLayout.remove(conditionCheckbox);
            showSecondConcept = false;
        } else {
            componentRuleLayout.add(secondVariable);
            componentRuleLayout.remove(thirdVariable);
            componentRuleLayout.remove(conditionCheckbox);
            showSecondConcept = true;
            showThirdConcept = false;
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
        sBuilder.append(secondCombobox.getValue() + " ");
        sBuilder.append(firstVariable.getValue() + " ");
        sBuilder.append(thirdCombobox.getValue() + " ");
        if (showSecondConcept) {
            sBuilder.append(secondVariable.getValue() + " ");
        }
        if (showThirdConcept) {
            sBuilder.append(thirdVariable.getValue() + " ");
            if (conditionCheckbox.getValue()) {
                sBuilder.append(newCondition.getString() + " ");
            }
        }
        return sBuilder.toString();
    }
}
