package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.Arrays;
import org.archcnl.ui.common.andtriplets.triplet.ConceptSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.PredicateSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.conditioncomponents.ConditionStatement;

public class IfStatementComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout firstRowComponentRuleLayout, secondRowComponentRuleLayout;
    private ConditionStatement firstCondition, secondCondition;
    private PredicateSelectionComponent one_firstVariable, six_thirdVariable;
    private ComboBox<String> two_firstModifier, five_secondModifier, seven_thirdModifier;
    private ConceptSelectionComponent three_secondVariable, eight_fourthVariable;
    private Checkbox four_firstAddConditionCheckbox, nine_secondAddConditionCheckbox;

    public IfStatementComponent() {
        this.setMargin(false);
        this.setPadding(false);

        initializeLayout();
    }

    private void initializeLayout() {

        firstCondition = createConditionComponent();
        secondCondition = createConditionComponent();
        firstRowComponentRuleLayout = new HorizontalLayout();

        one_firstVariable = createRelationComboBox();

        two_firstModifier = new ComboBox<String>("Modifier", Arrays.asList(" ", "a", "an"));
        two_firstModifier.setValue("a");

        three_secondVariable = createConceptComboBox();

        four_firstAddConditionCheckbox = new Checkbox("that... (add condition)");
        four_firstAddConditionCheckbox.addClickListener(
                e -> addConditionBlock(true, four_firstAddConditionCheckbox.getValue()));

        firstRowComponentRuleLayout.setVerticalComponentAlignment(
                Alignment.END, four_firstAddConditionCheckbox);
        firstRowComponentRuleLayout.add(
                one_firstVariable,
                two_firstModifier,
                three_secondVariable,
                four_firstAddConditionCheckbox);

        secondRowComponentRuleLayout = new HorizontalLayout();

        five_secondModifier = new ComboBox<>("Modifier", ", then it must");
        five_secondModifier.setValue(", then it must");

        six_thirdVariable = createRelationComboBox();

        seven_thirdModifier =
                new ComboBox<String>("Modifier", Arrays.asList("this", "this a", "this an"));
        seven_thirdModifier.setValue("this");

        eight_fourthVariable = createConceptComboBox();

        nine_secondAddConditionCheckbox = new Checkbox("that... (add condition)");
        nine_secondAddConditionCheckbox.addClickListener(
                e -> addConditionBlock(false, nine_secondAddConditionCheckbox.getValue()));

        secondRowComponentRuleLayout.setVerticalComponentAlignment(
                Alignment.END, nine_secondAddConditionCheckbox);
        secondRowComponentRuleLayout.add(
                five_secondModifier,
                six_thirdVariable,
                seven_thirdModifier,
                eight_fourthVariable,
                nine_secondAddConditionCheckbox);

        add(firstRowComponentRuleLayout, secondRowComponentRuleLayout);
    }
    
    private ConceptSelectionComponent createConceptComboBox() {
    	ConceptSelectionComponent newConceptComboBox = new ConceptSelectionComponent();
        newConceptComboBox.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        newConceptComboBox.setPlaceholder("Concept");
        newConceptComboBox.setLabel("Concept");
        return newConceptComboBox;
    }
    
    private PredicateSelectionComponent createRelationComboBox() {
        PredicateSelectionComponent newRelationComboBox = new PredicateSelectionComponent();
        newRelationComboBox.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        newRelationComboBox.setPlaceholder("Relation");
        newRelationComboBox.setLabel("Relation");
        return newRelationComboBox;
    }

    private ConditionStatement createConditionComponent() {
        ConditionStatement condition = new ConditionStatement();
        condition.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        condition.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        return condition;
    }

    private void addConditionBlock(Boolean conditionOne, Boolean showCondition) {
        if (conditionOne) {
            if (showCondition) {
                addComponentAtIndex(1, firstCondition);
                return;
            }
            remove(firstCondition);
            return;
        }

        if (showCondition) {
            addComponentAtIndex(3, secondCondition);
            return;
        }
        remove(secondCondition);
    }

    @Override
    public String getRuleString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(one_firstVariable.getValue() + " ");
        sBuilder.append(two_firstModifier.getValue() + " ");
        sBuilder.append(three_secondVariable.getValue());
        if (four_firstAddConditionCheckbox.getValue()) {
            sBuilder.append(" " + firstCondition.getRuleString());
        }
        sBuilder.append(five_secondModifier.getValue() + " ");
        sBuilder.append(six_thirdVariable.getValue() + " ");
        sBuilder.append(seven_thirdModifier.getValue() + " ");
        sBuilder.append(eight_fourthVariable.getValue());
        if (nine_secondAddConditionCheckbox.getValue()) {
            sBuilder.append(" " + secondCondition.getRuleString());
        }
        return sBuilder.toString();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
