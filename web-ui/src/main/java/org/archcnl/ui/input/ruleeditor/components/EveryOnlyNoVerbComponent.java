package org.archcnl.ui.input.ruleeditor.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.util.Arrays;
import java.util.List;

public class EveryOnlyNoVerbComponent extends RuleComponent {

    private static final long serialVersionUID = 1L;
    private ComboBox<String> one_firstCombobox, three_secondCombobox, seven_thirdCombobox;
    private RelationTextfieldComponent two_firstVariable;
    private ConceptTextfieldComponent four_secondVariable, five_thirdVariable;
    private Checkbox six_addConditionCheckbox;
    private HorizontalLayout componentRuleLayout, andOrBlockLayout;
    private boolean isAndOrBlock = false;
    private ConditionComponent newCondition;
    private EveryOnlyNoVerbComponent newVerb;
    private Label boxTitel;
    private SelectionState currentState;
    private Component[] buildingBlock;


    private enum SelectionState {
        ONETWO (0, true, true, false, false, false, true),
        ONETWOSIX(1, true, true, false, false, false, true),
        ONETWOTHREE(2, true, true,true, false, false, false),
        ONETWOTHREEFOURFIVE(3, true, true, true, true,true, true),
        ONETWOTHREEFOURFIVESIX(4, true, true, true, true,true ,true),
        ONETWOTHREEFOUR(5, true, true, true,true, false, false),
        ONETWOTHREEFOURB(6, true, true, true,true, false, true),
        ONETWOTHREEFOURSIX(7, true, true, true,true, false, true),
        TWO (8, false, true, false, false, false, true),
        TWOSIX(9, false, true, false, false, false, true),
        TWOTHREE(10, false, true, true, false, false, false),
        TWOTHREEFOURFIVE(11, false, true, true, true, true, true),
        TWOTHREEFOURFIVESIX(12, false, true, true, true, true, true),
        TWOTHREEFOUR(13, false, true, true, true, false, false),
        TWOTHREEFOURB(14, false, true, true, true, false, true),
        TWOTHREEFOURSIX(15, false, true, true, true, false, true);
        
        private int value;
        private boolean [] showElementsArray;
        
        private SelectionState(int value, boolean one, boolean two, boolean three, boolean four, boolean five, boolean six)
        {
            this.value = value;
            showElementsArray = new boolean[6];
            showElementsArray[0] = one;
            showElementsArray[1] = two;
            showElementsArray[2] = three;
            showElementsArray[3] = four;
            showElementsArray[4] = five;
            showElementsArray[5] = six;
        }        
        
        public SelectionState isAndOrBlock(SelectionState state)
        {
            if(state.value > 7)
                return state;
            return values()[ordinal() + 8];
        }
    
        public final boolean [] getShowArray()
        {
            return showElementsArray;
        }
    }

    public EveryOnlyNoVerbComponent(Boolean andOrBlock) {
        isAndOrBlock = andOrBlock;
        createComponents();
        initializeLayout();
        determineState();
    }

    private void createComponents() {
        boxTitel = new Label("Rule Statement");
        componentRuleLayout = new HorizontalLayout();

        List<String> firstModifierList =
                Arrays.asList("must", "can-only", "can", "must be", "must be a", "must be an");
        one_firstCombobox = new ComboBox<String>("Modifier", firstModifierList);
        one_firstCombobox.setValue("must");
        one_firstCombobox.addValueChangeListener(
                e -> {
                    determineState();
                });

        two_firstVariable = new RelationTextfieldComponent();
        two_firstVariable.setLabel("Relation");

        List<String> secondModifierList =
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
        three_secondCombobox = new ComboBox<String>("Modifier", secondModifierList);
        three_secondCombobox.setValue("a");
        three_secondCombobox.addValueChangeListener(
                e -> {
                    determineState();
                });

        four_secondVariable = new ConceptTextfieldComponent();
        four_secondVariable.setLabel("Concept");

        five_thirdVariable = new ConceptTextfieldComponent();
        five_thirdVariable.setLabel("Concept");

        six_addConditionCheckbox = new Checkbox("that... (add condition)");
        six_addConditionCheckbox.addClickListener(e -> addConditionBlock(six_addConditionCheckbox.getValue()));
        componentRuleLayout.setVerticalComponentAlignment(Alignment.END, six_addConditionCheckbox);
        newCondition = new ConditionComponent();

        andOrBlockLayout = new HorizontalLayout();
        andOrBlockLayout.setMargin(false);
        List<String> secondStatements = Arrays.asList("-", "and", "or");
        seven_thirdCombobox = new ComboBox<String>("And / Or (Optional)", secondStatements);
        seven_thirdCombobox.setValue("-");
        seven_thirdCombobox.addValueChangeListener(
                e -> {
                    addAndOrBlock(seven_thirdCombobox.getValue());
                });
        andOrBlockLayout.add(seven_thirdCombobox);
        
        buildingBlock = new Component[6];
        buildingBlock[0] = one_firstCombobox;
        buildingBlock[1] = two_firstVariable;
        buildingBlock[2] = three_secondCombobox;
        buildingBlock[3] = four_secondVariable;
        buildingBlock[4] = five_thirdVariable;
        buildingBlock[5] = six_addConditionCheckbox;
    }

    private void initializeLayout() {
        if (isAndOrBlock) {
            this.setPadding(false);
            add(componentRuleLayout);
        } else {
            this.setPadding(true);
            this.getStyle().set("border", "1px solid black");
            componentRuleLayout.add(one_firstCombobox);
            add(boxTitel, componentRuleLayout, andOrBlockLayout);
        }
    }

    private void determineState() {
        String firstModifier = one_firstCombobox.getValue();
        if (firstModifier.equals("must be")
                || firstModifier.equals("must be a")
                || firstModifier.equals("must be an")) {
            currentState = SelectionState.ONETWO;
            if (six_addConditionCheckbox.getValue()) {
                currentState = SelectionState.ONETWOSIX;
            }
        } else {
            String secondModifier = three_secondCombobox.getValue();
            if (secondModifier.equals("equal-to anything") || secondModifier.equals("anything")) {
                currentState = SelectionState.ONETWOTHREE;
            } else if (secondModifier.equals("at-most")
                    || secondModifier.equals("at-least")
                    || secondModifier.equals("exactly")
                    || secondModifier.equals("equal-to at-most")
                    || secondModifier.equals("equal-to at-least")
                    || secondModifier.equals("equal-to exactly")) {
                currentState = SelectionState.ONETWOTHREEFOURFIVE;
                if (six_addConditionCheckbox.getValue()) {
                    currentState = SelectionState.ONETWOTHREEFOURFIVESIX;
                }
            } else if (secondModifier.equals("equal-to")) {
                currentState = SelectionState.ONETWOTHREEFOUR;
            } else {
                currentState = SelectionState.ONETWOTHREEFOURB;
                if (six_addConditionCheckbox.getValue()) {
                    currentState = SelectionState.ONETWOTHREEFOURSIX;
                }
            }
        }
        if(isAndOrBlock)
        {
            currentState = currentState.isAndOrBlock(currentState);
        }
        updateUI();
    }

    private void updateUI() {
        componentRuleLayout.removeAll();
        four_secondVariable.setPlaceholder("Concept");
        four_secondVariable.setLabel("Concept"); 
        
        boolean[] boolArray = currentState.getShowArray();
        for(int i = 0; i < boolArray.length; i++)
        {
            if(boolArray[i])
            {
                componentRuleLayout.add(buildingBlock[i]);
            }            
        }
        
        if(boolArray[4])
        {
            four_secondVariable.setPlaceholder("+/- [0-9]");
            four_secondVariable.setLabel("Number");
        }
        
        if(currentState.value == 5 || currentState.value == 10)
        {
          four_secondVariable.setPlaceholder("+/- [0-9] / String");
          four_secondVariable.setLabel("Number or String");
        }              
    }

    public String getString() {      
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(one_firstCombobox.getValue() + " ");
        sBuilder.append(two_firstVariable.getValue() + " ");
       
        boolean[] boolArray = currentState.getShowArray();
        if(boolArray[2])
        {
            sBuilder.append(three_secondCombobox.getValue() + " ");
        }
        if(boolArray[3])
        {
            sBuilder.append(four_secondVariable.getValue() + " ");
        }
        if(boolArray[4])
        {
            sBuilder.append(five_thirdVariable.getValue() + " ");
        }
        if(boolArray[5])
        {
            if(six_addConditionCheckbox.getValue())
            {
                sBuilder.append(newCondition.getString() + " ");
            }
        }      
        if(!seven_thirdCombobox.getValue().equals("-"))
        {
            sBuilder.append(newVerb.getString());
        }
        return sBuilder.toString();
    }

    private void addConditionBlock(Boolean showCondition) {
        if (showCondition) {
            //addComponentAtIndex(2, newCondition);
            add(newCondition);
        } else {
            remove(newCondition);
        }
    }

    private void addAndOrBlock(String andOrSelection) {
        if (newVerb == null) {
            newVerb = new EveryOnlyNoVerbComponent(true);
        }
        if (!andOrSelection.equals("-")) {
            andOrBlockLayout.add(newVerb);
        } else {
            andOrBlockLayout.remove(newVerb);
        }
    }
}
