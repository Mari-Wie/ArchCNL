package org.archcnl.ui.input.ruleeditor.components.verbcomponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import java.util.Arrays;
import org.archcnl.ui.input.ruleeditor.components.ConceptTextfieldComponent;
import org.archcnl.ui.input.ruleeditor.components.ConditionComponent;
import org.archcnl.ui.input.ruleeditor.components.RelationTextfieldComponent;
import org.archcnl.ui.input.ruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.input.ruleeditor.events.AddAndOrVerbComponentEvent;
import org.archcnl.ui.input.ruleeditor.events.RemoveAndOrVerbComponentEvent;
import org.archcnl.ui.input.ruleeditor.events.ShowAndOrBlockEvent;

public class EveryOnlyNoVerbComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout horizontalRowLayout, componentRuleLayout;
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
                            "equal-to",
                            "equal-to a",
                            "equal-to an",
                            "equal-to at-most",
                            "equal-to at-least",
                            "equal-to exactly"));

    /**
     * SelectionState enumeration is used to determine how to update the UI. Each state has six
     * booleans corresponding to the six possible components. Different SelectionState require the
     * showing of different components.
     */
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

        private boolean[] showComponentsBooleanArray;

        private SelectionState(
                boolean one, boolean two, boolean three, boolean four, boolean five, boolean six) {
            showComponentsBooleanArray = new boolean[6];
            showComponentsBooleanArray[0] = one;
            showComponentsBooleanArray[1] = two;
            showComponentsBooleanArray[2] = three;
            showComponentsBooleanArray[3] = four;
            showComponentsBooleanArray[4] = five;
            showComponentsBooleanArray[5] = six;
        }

        public final boolean[] getShowComponentBooleanArray() {
            return showComponentsBooleanArray;
        }

        /**
         * Is only called when the "addCondition" check box is visible and has been checked. Returns
         * the antecedent state which in the case of "ONETWO", "ONETWOTHREEFOURFIVE" and
         * "ONETWOTHREEFOURB" means the equivalent state with an active condition.
         */
        public SelectionState switchToStateWithActivatedCondition() {
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

        horizontalRowLayout = new HorizontalLayout();
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
                e -> showConditionBlock(six_addConditionCheckbox.getValue()));
        componentRuleLayout.setVerticalComponentAlignment(Alignment.END, six_addConditionCheckbox);

        buildingBlock = new Component[6];
        buildingBlock[0] = one_firstCombobox;
        buildingBlock[1] = two_firstVariable;
        buildingBlock[2] = three_secondCombobox;
        buildingBlock[3] = four_secondVariable;
        buildingBlock[4] = five_thirdVariable;
        buildingBlock[5] = six_addConditionCheckbox;

        horizontalRowLayout.add(componentRuleLayout);
        initializeAndOrButtons();
        add(horizontalRowLayout);
    }

    private void initializeFirstCombobox() {
        if (isAndOrBlock) {
            one_firstCombobox =
                    new ComboBox<String>("And / Or (Optional)", Arrays.asList("-", "and", "or"));
            one_firstCombobox.setValue("-");
        } else {
            one_firstCombobox =
                    new ComboBox<String>(
                            "Modifier",
                            Arrays.asList(
                                    "must",
                                    "can-only",
                                    "can",
                                    "must be",
                                    "must be a",
                                    "must be an"));
            one_firstCombobox.setValue("must");
            secondModifierList.addAll(Arrays.asList("anything", "equal-to anything"));
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

        if (currentState.showComponentsBooleanArray[5]) {
            if (six_addConditionCheckbox.getValue()) {
                currentState = currentState.switchToStateWithActivatedCondition();
            }
        }

        updateUI();
    }

    private void updateUI() {
        componentRuleLayout.removeAll();
        four_secondVariable.setPlaceholder("Concept");
        four_secondVariable.setLabel("Concept");

        // Show all UI elements according to current state
        boolean[] boolArray = currentState.getShowComponentBooleanArray();
        for (int i = 0; i < boolArray.length; i++) {
            if (boolArray[i]) {
                componentRuleLayout.add(buildingBlock[i]);
            }
        }

        // Change component descriptors if necessary
        if (boolArray[3]) {
            if (currentState == SelectionState.ONETWOTHREEFOURFIVE
                    || currentState == SelectionState.ONETWOTHREEFOURFIVESIX) {
                four_secondVariable.setPlaceholder("+/- [0-9]");
                four_secondVariable.setLabel("Number");
            }

            if (currentState == SelectionState.ONETWOTHREEFOUR) {
                four_secondVariable.setPlaceholder("+/- [0-9] / String");
                four_secondVariable.setLabel("Number or String");
            }
        }
        // Hide / Show condition
        showConditionBlock(boolArray[5] && six_addConditionCheckbox.getValue());

        // Hide / Show AndOr Block
        switch (currentState) {
            case ONETWO:
            case ONETWOSIX:
            case ONETWOTHREE:
                fireEvent(new ShowAndOrBlockEvent(this, true, false));
                break;
            default:
                fireEvent(new ShowAndOrBlockEvent(this, true, true));
                break;
        }
    }

    private void initializeAndOrButtons() {
        if (isAndOrBlock) {
            HorizontalLayout boxButtonLayout = new HorizontalLayout();
            Button addButton =
                    new Button(
                            new Icon(VaadinIcon.PLUS),
                            click -> fireEvent(new AddAndOrVerbComponentEvent(this, true)));
            Button deleteButton =
                    new Button(
                            new Icon(VaadinIcon.TRASH),
                            click -> fireEvent(new RemoveAndOrVerbComponentEvent(this, true)));
            boxButtonLayout.add(addButton, deleteButton);
            horizontalRowLayout.add(boxButtonLayout);
            horizontalRowLayout.setVerticalComponentAlignment(Alignment.END, boxButtonLayout);
        }
    }

    private void showConditionBlock(Boolean showCondition) {
        if (showCondition) {
            add(newCondition);
        } else {
            remove(newCondition);
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    @Override
    public String getString() {
        // This is an empty AndOr Block and is ignored
        if (one_firstCombobox.getValue().equals("-")) {
            return "";
        }

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
