package org.archcnl.ui.input.ruleeditor.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.Arrays;
import java.util.List;

public class VerbComponent extends RuleComponent {

    private static final long serialVersionUID = 1L;
    private ComboBox<String> firstCombobox, secondCombobox, thirdCombobox;
    private RelationTextfieldComponent firstVariable;
    private ConceptTextfieldComponent secondVariable, thirdVariable;
    private HorizontalLayout componentRuleLayout, andOrBlockLayout;
    private boolean showSecondVariable = true, showSecondCombobox = true, showThirdVariable = false, isAndOrBlock = false;
    private Checkbox addConditionCheckbox;
    private ConditionComponent newCondition;
    private VerbComponent newVerb;

    public VerbComponent(Boolean andOrBlock) {
        isAndOrBlock = andOrBlock;      
        
        componentRuleLayout = new HorizontalLayout();
        List<String> firstStatements =
                Arrays.asList("must", "can-only", "can", "must be", "must be a", "must be an");
        firstCombobox = new ComboBox<String>("Modifier", firstStatements);
        firstCombobox.setValue("must");
        firstCombobox.addValueChangeListener(
                e -> {
                    firstComboboxListener(firstCombobox.getValue());
                });
        
        firstVariable = new RelationTextfieldComponent();
        firstVariable.setLabel("Relation");

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

        secondVariable = new ConceptTextfieldComponent();
        secondVariable.setLabel("Concept");
        
        thirdVariable = new ConceptTextfieldComponent();
        thirdVariable.setLabel("Concept");
        
        addConditionCheckbox = new Checkbox("that... (add condition)");
        addConditionCheckbox.addClickListener(e -> addCondition(addConditionCheckbox.getValue()));
        componentRuleLayout.setVerticalComponentAlignment(Alignment.END, addConditionCheckbox);
        newCondition = new ConditionComponent();
        
        andOrBlockLayout = new HorizontalLayout();
        andOrBlockLayout.setMargin(false);
        List<String> secondStatements =
                Arrays.asList("-", "and", "or");
        thirdCombobox = new ComboBox<String>("And / Or (Optional)", secondStatements);
        thirdCombobox.setValue("-");
        thirdCombobox.addValueChangeListener(
                e -> {
                    addAndOrBlock(thirdCombobox.getValue());
                });
        andOrBlockLayout.add(thirdCombobox);
         
        Label boxTitel = new Label("Rule Statement");
        
        if(andOrBlock)
        {
            this.setPadding(false);
            componentRuleLayout.add(firstVariable, secondCombobox, secondVariable);
            add(componentRuleLayout);
        }
        else
        {
            this.setPadding(true);
            this.getStyle().set("border", "1px solid black");            
            componentRuleLayout.add(firstCombobox, firstVariable, secondCombobox, secondVariable);
            add(boxTitel, componentRuleLayout, andOrBlockLayout);
        }               
    }

    private void firstComboboxListener(String value) {
        if (value.equals("must be") || value.equals("must be a") || value.equals("must be an"))
        {
            componentRuleLayout.remove(secondCombobox, secondVariable);
            componentRuleLayout.add(addConditionCheckbox);
            showSecondCombobox = false;
            showSecondVariable = false;
        }
        else
        {
            componentRuleLayout.add(secondCombobox, secondVariable);
            componentRuleLayout.remove(addConditionCheckbox);
            showSecondCombobox = true;
            showSecondVariable = true;
        }
    }
    
    private void secondComboboxListener(String value) {
        componentRuleLayout.add(secondVariable);
        componentRuleLayout.remove(thirdVariable, addConditionCheckbox);
        secondVariable.setPlaceholder("Concept");
        secondVariable.setLabel("Concept");
        showSecondVariable = true;
        showThirdVariable = false;
        
        if (value.equals("at-most")
                || value.equals("at-least")
                || value.equals("exactly")
                || value.equals("equal-to at-most")
                || value.equals("equal-to at-least")
                || value.equals("equal-to exactly")) {
            componentRuleLayout.add(thirdVariable, addConditionCheckbox);
            secondVariable.setPlaceholder("+/- [0-9]");
            secondVariable.setLabel("Number");
            showThirdVariable = true;
        } else if (value.equals("anything") || value.equals("equal-to anything")) {
            componentRuleLayout.remove(secondVariable, thirdVariable, addConditionCheckbox);
            showSecondVariable = false;
        } else if(value.equals("equal-to")) {
            secondVariable.setPlaceholder("+/- [0-9] / char");
            secondVariable.setLabel("Number or Char");
        }
        else {
            secondVariable.setPlaceholder("Concept");
            secondVariable.setLabel("Concept");
        }
    }

    public void addCondition(Boolean showCondition) {
        if (showCondition) {
            addComponentAtIndex(2, newCondition);
        } else {
            remove(newCondition);
        }
    }
    
    private void addAndOrBlock(String addBlock) {
        if (newVerb == null) {
            newVerb = new VerbComponent(true);
        }
        if (!addBlock.equals("-")) {
            andOrBlockLayout.add(newVerb);
        } else {
            andOrBlockLayout.remove(newVerb);
        }
    }

    public String getString() {
        StringBuilder sBuilder = new StringBuilder();
        
        if(!isAndOrBlock)
        {
            sBuilder.append(firstCombobox.getValue() + " ");
        }                      
        sBuilder.append(firstVariable.getValue() + " ");
        if (showSecondCombobox) {
            sBuilder.append(secondCombobox.getValue() + " ");
        }
        if (showSecondVariable) {
            sBuilder.append(secondVariable.getValue() + " ");
        }
        if (showThirdVariable) {
            sBuilder.append(thirdVariable.getValue() + " ");
            if (addConditionCheckbox.getValue()) {
                sBuilder.append(newCondition.getString() + " ");
            }
        }
        if(!thirdCombobox.getValue().equals("-"))
        {
            sBuilder.append(thirdCombobox.getValue() + " ");
            sBuilder.append(newVerb.getString());
        }
        
        return sBuilder.toString();
    }
}
