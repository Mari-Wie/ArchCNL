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
    private Checkbox statementCheckbox;
    private ConditionComponent newCondition;

    public SubjectComponent() {
        this.add(new Label("Rule Subject"));
        this.getStyle().set("border", "1px solid black");
        HorizontalLayout mainSubjectBox = new HorizontalLayout();
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

        firstConcept = new TextField("Concept");
        // firstConcept.setPlaceholder("Aggregate");
        statementCheckbox = new Checkbox("that... (add condition)");
        mainSubjectBox.setVerticalComponentAlignment(Alignment.END, statementCheckbox);
        statementCheckbox.addClickListener(e -> addCondition(statementCheckbox.getValue()));
        mainSubjectBox.add(firstCombobox, firstConcept, statementCheckbox);

        newCondition = new ConditionComponent();
        add(mainSubjectBox);
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
        sBuilder.append(firstConcept.getValue() + " ");
        if (statementCheckbox.getValue()) {
            sBuilder.append(newCondition.getString());
        }
        return sBuilder.toString();
    }
}
