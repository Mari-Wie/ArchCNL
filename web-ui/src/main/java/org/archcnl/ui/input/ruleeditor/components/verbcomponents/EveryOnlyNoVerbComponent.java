package org.archcnl.ui.input.ruleeditor.components.verbcomponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;

import org.archcnl.ui.input.ruleeditor.components.ConceptTextfieldComponent;
import org.archcnl.ui.input.ruleeditor.components.ConditionComponent;
import org.archcnl.ui.input.ruleeditor.components.RelationTextfieldComponent;
import org.archcnl.ui.input.ruleeditor.components.RuleComponentInterface;

public class EveryOnlyNoVerbComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout componentRuleLayout;
    private ConditionComponent newCondition;
    private ComboBox<String> one_firstCombobox, three_secondCombobox;
    private RelationTextfieldComponent two_firstVariable;
    private ConceptTextfieldComponent four_secondVariable, five_thirdVariable;
    private Checkbox six_addConditionCheckbox;
    private Component[] buildingBlock;
    private SelectionState currentState;
    private boolean isAndOrBlock = false;   
    private ArrayList<String> secondModifierList =
            new ArrayList<>(
                    Arrays.asList(
                            " ",
                            "a",
                            "an",
                            "at-most",
                            "at-least",
                            "exactly",
                            "equal-to at-most",
                            "equal-to at-least",
                            "equal-to exactly"));

    private enum SelectionState {
        ONE(true, false, false, false, false, false),
        ONETWO(true, true, false, false, false, true),
        ONETWOSIX(true, true, false, false, false, true),
        ONETWOTHREE(true, true, true, false, false, false),
        ONETWOTHREEFOURFIVE(true, true, true, true, true, true),
        ONETWOTHREEFOURFIVESIX(true, true, true, true, true, true),
        ONETWOTHREEFOUR(true, true, true, true, false, false),
        ONETWOTHREEFOURB(true, true, true, true, false, true),
        ONETWOTHREEFOURSIX(true, true, true, true, false, true);

        private boolean[] showComponentsArray;

        private SelectionState(
                boolean one, boolean two, boolean three, boolean four, boolean five, boolean six) {
            showComponentsArray = new boolean[6];
            showComponentsArray[0] = one;
            showComponentsArray[1] = two;
            showComponentsArray[2] = three;
            showComponentsArray[3] = four;
            showComponentsArray[4] = five;
            showComponentsArray[5] = six;
        }

        /** @return a boolean array describing what UI components are to be shown in the current state */
        public final boolean[] getShowComponentBooleanArray() {
            return showComponentsArray;
        }

        /**
         * Can only be called in a state that shows the "addCondition" check box.
         *
         * @return The state of current state when the "addCondition" check box is checked. e.g
         *     State ONETWO -> ONETWOSIX
         */
        public SelectionState addCondition() {
            return values()[ordinal() + 1];
        }
    }

    public EveryOnlyNoVerbComponent(Boolean andOrBlock) {
        this.setMargin(false);
        this.setPadding(false);
        isAndOrBlock = andOrBlock;
        initializeLayout();
        determineState();
    }

    private void initializeLayout() {
        newCondition = new ConditionComponent();

        componentRuleLayout = new HorizontalLayout();
        initializeFirstCombobox();
        two_firstVariable = new RelationTextfieldComponent();
        three_secondCombobox = new ComboBox<String>("Modifier", secondModifierList);
        three_secondCombobox.setValue("a");
        three_secondCombobox.addValueChangeListener(
                e -> {
                    determineState();
                });
        four_secondVariable = new ConceptTextfieldComponent();
        five_thirdVariable = new ConceptTextfieldComponent();
        six_addConditionCheckbox = new Checkbox("that... (add condition)");
        six_addConditionCheckbox.addClickListener(
                e -> addConditionBlock(six_addConditionCheckbox.getValue()));
        componentRuleLayout.setVerticalComponentAlignment(Alignment.END, six_addConditionCheckbox);

        buildingBlock = new Component[6];
        buildingBlock[0] = one_firstCombobox;
        buildingBlock[1] = two_firstVariable;
        buildingBlock[2] = three_secondCombobox;
        buildingBlock[3] = four_secondVariable;
        buildingBlock[4] = five_thirdVariable;
        buildingBlock[5] = six_addConditionCheckbox;

        add(componentRuleLayout);
    }

    private void initializeFirstCombobox() {
        if (isAndOrBlock) {
            one_firstCombobox = new ComboBox<String>("And / Or (Optional)", Arrays.asList("-", "and", "or"));
            one_firstCombobox.setValue("-");
        } else {
            one_firstCombobox = new ComboBox<String>("Modifier", Arrays.asList("must", "can-only", "can", "must be", "must be a", "must be an"));
            one_firstCombobox.setValue("must");
            //These options aren't available for a AndOrComponent
            secondModifierList.addAll(Arrays.asList("anything", "equal-to", "equal-to anything"));
        }
        one_firstCombobox.addValueChangeListener(
                e -> {
                    determineState();
                });
    }

    private void determineState() {
        String firstModifier = one_firstCombobox.getValue();
        if (firstModifier.equals("-")) {
            currentState = SelectionState.ONE;
        } else if (firstModifier.equals("must be")
                || firstModifier.equals("must be a")
                || firstModifier.equals("must be an")) {
            currentState = SelectionState.ONETWO;
        } else {
            String secondModifier = three_secondCombobox.getValue();
            if (secondModifier.equals("equal-to anything") || secondModifier.equals("anything")) {
                currentState = SelectionState.ONETWOTHREE;
            } else if (secondModifier.equals("equal-to")) {
                currentState = SelectionState.ONETWOTHREEFOUR;
            } else if (secondModifier.equals("at-most")
                    || secondModifier.equals("at-least")
                    || secondModifier.equals("exactly")
                    || secondModifier.equals("equal-to at-most")
                    || secondModifier.equals("equal-to at-least")
                    || secondModifier.equals("equal-to exactly")) {
                currentState = SelectionState.ONETWOTHREEFOURFIVE;
            } else {
                currentState = SelectionState.ONETWOTHREEFOURB;
            }
        }

        if (currentState.showComponentsArray[5]) {
            if (six_addConditionCheckbox.getValue()) {
                currentState = currentState.addCondition();
            }
        }
        updateUI();
    }

    private void updateUI() {
        componentRuleLayout.removeAll();
        four_secondVariable.setPlaceholder("Concept");
        four_secondVariable.setLabel("Concept");

        boolean[] boolArray = currentState.getShowComponentBooleanArray();
        for (int i = 0; i < boolArray.length; i++) {
            if (boolArray[i]) {
                componentRuleLayout.add(buildingBlock[i]);
            }
        }

        if (boolArray[4]) {
            four_secondVariable.setPlaceholder("+/- [0-9]");
            four_secondVariable.setLabel("Number");
            
            if (currentState == SelectionState.ONETWOTHREEFOUR) {
                four_secondVariable.setPlaceholder("+/- [0-9] / String");
                four_secondVariable.setLabel("Number or String");
            }
        }      
    }

    private void addConditionBlock(Boolean showCondition) {
        if (showCondition) {
            add(newCondition);
        } else {
            remove(newCondition);
        }
    }

    @Override
    public String getString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(one_firstCombobox.getValue() + " ");
        sBuilder.append(two_firstVariable.getValue() + " ");

        boolean[] boolArray = currentState.getShowComponentBooleanArray();
        if (boolArray[2]) {
            sBuilder.append(three_secondCombobox.getValue() + " ");
        }
        if (boolArray[3]) {
            sBuilder.append(four_secondVariable.getValue() + " ");
        }
        if (boolArray[4]) {
            sBuilder.append(five_thirdVariable.getValue());
        }
        if (boolArray[5]) {
            if (six_addConditionCheckbox.getValue()) {
                sBuilder.append(" " + newCondition.getString());
            }
        }
        return sBuilder.toString();
    }
}
