package org.archcnl.ui.input.ruleeditor.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Arrays;
import java.util.List;
import org.archcnl.ui.input.ruleeditor.events.DetermineVerbComponentEvent;

public class SubjectComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout subjectLayout;
    private ConditionComponent newCondition;
    private ComboBox<String> one_firstCombobox;
    private ConceptTextfieldComponent two_firstConcept;
    private Checkbox three_conditionCheckbox;   
    private boolean showFirstConcept = true;

    public SubjectComponent() {
        this.add(new Label("Rule Subject"));
        this.getStyle().set("border", "1px solid black");
        this.setMargin(false);
        
        initializeLayout();        
    }
    
    private void initializeLayout() {
        newCondition = new ConditionComponent();
        
        subjectLayout = new HorizontalLayout();
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
        one_firstCombobox = new ComboBox<String>("Modifier", firstStatements);
        one_firstCombobox.setValue("Every");
        one_firstCombobox.addValueChangeListener(
                e -> {
                    updateUI(one_firstCombobox.getValue());
                });
        two_firstConcept = new ConceptTextfieldComponent();
        two_firstConcept.setLabel("Concept");
        three_conditionCheckbox = new Checkbox("that... (add condition)");
        three_conditionCheckbox.addClickListener(e -> addCondition(three_conditionCheckbox.getValue()));
        subjectLayout.setVerticalComponentAlignment(Alignment.END, three_conditionCheckbox);
        subjectLayout.add(one_firstCombobox, two_firstConcept, three_conditionCheckbox);
       
        add(subjectLayout);
    }

    private void updateUI(String value) {
        if (value.equals("Nothing")) {
            subjectLayout.remove(two_firstConcept, three_conditionCheckbox);
            showFirstConcept = false;
        } else if (value.equals("Fact:")) {
            subjectLayout.remove(three_conditionCheckbox);
        } else {
            subjectLayout.add(two_firstConcept, three_conditionCheckbox);
            showFirstConcept = true;
        }
        fireEvent(new DetermineVerbComponentEvent(this, true));
    }

    public void addCondition(Boolean showCondition) {
        if (showCondition) {
            add(newCondition);
        } else {
            remove(newCondition);
        }
    }

    public String getFirstModifier() {
        return one_firstCombobox.getValue();
    }

    @Override
    public String getString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(one_firstCombobox.getValue() + " ");
        if (showFirstConcept) {
            sBuilder.append(two_firstConcept.getValue() + " ");
            if (three_conditionCheckbox.getValue()) {
                sBuilder.append(newCondition.getString());
            }
        }
        return sBuilder.toString();
    }
}
