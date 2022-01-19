package org.archcnl.ui.input.ruleeditor.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Arrays;
import java.util.List;

public class SubjectComponent extends RuleComponent {

    private static final long serialVersionUID = 1L;
    private ComboBox<String> firstCombobox;
    private TextField firstConcept;
    private Checkbox conditionCheckbox;
    private ConditionComponent newCondition;
    private HorizontalLayout mainSubjectBox;
    private boolean showFirstConcept = true;

    public SubjectComponent() {
        this.add(new Label("Rule Subject"));
        this.getStyle().set("border", "1px solid black");
        this.setMargin(false);
        
        mainSubjectBox = new HorizontalLayout();        
        List<String> firstStatements =
                Arrays.asList(
                        "Every",
                        "Only",
                        "If",
                        "Nothing",
                        "No",
                        "Fact:",
                        "Every a",
                        "Every an",
                        "Only a",
                        "Only an",
                        "If a",
                        "If an",
                        "No a",
                        "No an");
        firstCombobox = new ComboBox<String>("Modifier", firstStatements);
        firstCombobox.setValue("Every");
        firstCombobox.addValueChangeListener(
                e -> {
                    firstComboboxListener(firstCombobox.getValue());
                });

        firstConcept = new TextField("Concept");

        conditionCheckbox = new Checkbox("that... (add condition)");
        conditionCheckbox.addClickListener(e -> addCondition(conditionCheckbox.getValue()));
        mainSubjectBox.setVerticalComponentAlignment(Alignment.END, conditionCheckbox);       
        mainSubjectBox.add(firstCombobox, firstConcept, conditionCheckbox);

        newCondition = new ConditionComponent();
        add(mainSubjectBox);
    }
    
    private void firstComboboxListener(String value) {
        if(value.equals("Nothing"))
        {
            mainSubjectBox.remove(firstConcept, conditionCheckbox);
            showFirstConcept = false;
        }
        else if(value.equals("Fact:"))
        {
            mainSubjectBox.remove(conditionCheckbox);
        }
        else
        {
            mainSubjectBox.add(firstConcept, conditionCheckbox);
            showFirstConcept = true;
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
        if(showFirstConcept)
        {
            sBuilder.append(firstConcept.getValue() + " ");
            if (conditionCheckbox.getValue()) {
                sBuilder.append(newCondition.getString());
            }
        }
        return sBuilder.toString();
    }
}
