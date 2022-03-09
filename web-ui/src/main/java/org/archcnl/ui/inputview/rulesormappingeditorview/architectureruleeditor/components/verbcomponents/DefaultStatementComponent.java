package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents;

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
import java.util.List;
import org.archcnl.ui.common.andtriplets.triplet.ConceptSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.PredicateSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.conditioncomponents.ConditionStatementComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.textfieldwidgets.VariableTextfieldWidget;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.AddAndOrStatementComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.RemoveAndOrVerbComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.ShowAndOrStatmentComponentEvent;

public class DefaultStatementComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout horizontalRowLayout, componentRuleLayout;
    private ConditionStatementComponent newCondition;
    private ComboBox<String> one_firstCombobox, three_secondCombobox;
    private PredicateSelectionComponent two_firstVariable;
    private VariableTextfieldWidget four_secondVariable;
    private ConceptSelectionComponent five_thirdVariable;
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
     * The enumeration SelectionState is used to indicate what UI components should be shown,
     * depending on the current selection. Each state has six booleans corresponding to the six
     * possible components.
     */
    private enum SelectionState {
        EMPTYANDORBLOCK(true, false, false, false, false, false),
        MUSTBEBRANCH(true, false, false, false, true, true),
        MUSTBEBRANCHWITHCONDITION(true, false, false, false, true, true),
        ANYTHINGBRANCH(true, true, true, false, false, false),
        DEFAULTNUMBERBRANCH(true, true, true, true, true, true),
        DEFAULTNUMBERBRANCHWITHCONDITION(true, true, true, true, true, true),
        EQUALTOVARIABLEBRANCH(true, true, true, true, false, false),
        DEFAULTBRANCH(true, true, true, false, true, true),
        DEFAULTBRANCHWITHCONDITION(true, true, true, false, true, true);

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
         * Returns the new state if the "addCondition" check box is checked. Will return the wrong
         * state if called in a state that can't have a condition.
         */
        public SelectionState switchToStateWithActivatedCondition() {
            return values()[ordinal() + 1];
        }
    }

    public DefaultStatementComponent(Boolean andOrBlock) {
        this.setMargin(false);
        this.setPadding(false);
        isAndOrBlock = andOrBlock;

        initializeLayout();
        determineState();
    }

    private void initializeLayout() {
        createCondtionComponent();

        horizontalRowLayout = new HorizontalLayout();
        componentRuleLayout = new HorizontalLayout();

        initializeFirstCombobox();
        createRelationComboBox();

        three_secondCombobox = new ComboBox<String>("Modifier", secondModifierList);
        three_secondCombobox.setValue("a");
        three_secondCombobox.addValueChangeListener(
                e -> {
                    determineState();
                });

        four_secondVariable = new VariableTextfieldWidget("/[+-]?[0-9]+");
        four_secondVariable.setPlaceholder("+/- [0-9] / String");
        four_secondVariable.setLabel("Integer or String");

        createConceptComboBox();

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

    private void createCondtionComponent() {
        newCondition = new ConditionStatementComponent();
        newCondition.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        newCondition.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
    }

    private void createRelationComboBox() {
        two_firstVariable = new PredicateSelectionComponent();
        two_firstVariable.setLabel("Relation");
        two_firstVariable.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
    }

    private void createConceptComboBox() {
        five_thirdVariable = new ConceptSelectionComponent();
        five_thirdVariable.setLabel("Concept");
        five_thirdVariable.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
    }

    private void initializeFirstCombobox() {
        if (isAndOrBlock) {
            one_firstCombobox =
                    new ComboBox<String>("And / Or (Optional)", Arrays.asList("-", "and", "or"));
            one_firstCombobox.setValue("-");
            one_firstCombobox.addValueChangeListener(
                    e -> {
                        determineState();
                    });
            return;
        }
        List<String> modifierList =
                Arrays.asList("must", "can-only", "can", "must be", "must be a", "must be an");
        one_firstCombobox = new ComboBox<String>("Modifier", modifierList);
        one_firstCombobox.setValue("must");
        one_firstCombobox.addValueChangeListener(
                e -> {
                    determineState();
                });

        secondModifierList.addAll(Arrays.asList("anything", "equal-to anything"));
    }

    private void initializeAndOrButtons() {
        if (isAndOrBlock) {
            HorizontalLayout boxButtonLayout = new HorizontalLayout();
            Button addButton =
                    new Button(
                            new Icon(VaadinIcon.PLUS),
                            click -> fireEvent(new AddAndOrStatementComponentEvent(this, true)));
            Button deleteButton =
                    new Button(
                            new Icon(VaadinIcon.TRASH),
                            click -> fireEvent(new RemoveAndOrVerbComponentEvent(this, true)));
            boxButtonLayout.add(addButton, deleteButton);
            horizontalRowLayout.add(boxButtonLayout);
            horizontalRowLayout.setVerticalComponentAlignment(Alignment.END, boxButtonLayout);
        }
    }

    private void determineState() {
        String firstModifier = one_firstCombobox.getValue();

        switch (firstModifier) {
            case "-":
                currentState = SelectionState.EMPTYANDORBLOCK;
                break;
            case "must be":
            case "must be a":
            case "must be an":
                currentState = SelectionState.MUSTBEBRANCH;
                break;
            default:
                String secondModifier = three_secondCombobox.getValue();
                switch (secondModifier) {
                    case "anything":
                    case "equal-to anything":
                        currentState = SelectionState.ANYTHINGBRANCH;
                        break;
                    case "equal-to":
                        currentState = SelectionState.EQUALTOVARIABLEBRANCH;
                        break;
                    case "at-most":
                    case "at-least":
                    case "exactly":
                    case "equal-to at-most":
                    case "equal-to at-least":
                    case "equal-to exactly":
                        currentState = SelectionState.DEFAULTNUMBERBRANCH;
                        break;
                    default:
                        currentState = SelectionState.DEFAULTBRANCH;
                        break;
                }
                break;
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

        // Show all UI elements according to current state
        boolean[] boolArray = currentState.getShowComponentBooleanArray();
        for (int i = 0; i < boolArray.length; i++) {
            if (boolArray[i]) {
                componentRuleLayout.add(buildingBlock[i]);
            }
        }

        // Change component descriptors if necessary
        if (boolArray[3]) {
            if (currentState == SelectionState.DEFAULTNUMBERBRANCH
                    || currentState == SelectionState.DEFAULTNUMBERBRANCHWITHCONDITION) {
                four_secondVariable.setPlaceholder("+/- [0-9]");
                four_secondVariable.setLabel("Integer");
            }

            if (currentState == SelectionState.EQUALTOVARIABLEBRANCH) {
                four_secondVariable.setPlaceholder("+/- [0-9] / String");
                four_secondVariable.setLabel("Integer or String");
            }
        }
        // Hide / Show condition
        showConditionBlock(boolArray[5] && six_addConditionCheckbox.getValue());

        // Hide or Show AndOrVerbComponent
        switch (currentState) {
            case MUSTBEBRANCH:
            case MUSTBEBRANCHWITHCONDITION:
            case ANYTHINGBRANCH:
                fireEvent(new ShowAndOrStatmentComponentEvent(this, true, false));
                break;
            default:
                fireEvent(new ShowAndOrStatmentComponentEvent(this, true, true));
                break;
        }
    }

    private void showConditionBlock(Boolean showCondition) {
        if (showCondition) {
            add(newCondition);
            return;
        }
        remove(newCondition);
    }

    @Override
    public String getRuleString() {
        // In this case this is an empty AndOr Block and is ignored
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
                sBuilder.append(" " + newCondition.getRuleString());
            }
        }
        return sBuilder.toString();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
