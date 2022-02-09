package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.Arrays;

import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.ConceptTextfieldComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.ConditionComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RelationTextfieldComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;

public class IfVerbComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout firstRowComponentRuleLayout, secondRowComponentRuleLayout;
    private ConditionComponent firstCondition, secondCondition;
    private RelationTextfieldComponent one_firstVariable, five_thirdVariable;
    private ConceptTextfieldComponent two_secondVariable, seven_fourthVariable;
    private Checkbox three_firstAddConditionCheckbox, eight_secondAddConditionCheckbox;
    private ComboBox<String> oneb_OptionalModifier, four_firstModifier, six_secondModifier;

    public IfVerbComponent() {
        this.setMargin(false);
        this.setPadding(false);
        initializeLayout();
    }

    private void initializeLayout() {
        firstCondition = new ConditionComponent();
        secondCondition = new ConditionComponent();

        firstRowComponentRuleLayout = new HorizontalLayout();
        one_firstVariable = new RelationTextfieldComponent();
        oneb_OptionalModifier = new ComboBox<String>("Modifier", Arrays.asList(" ", "a", "an"));
        oneb_OptionalModifier.setValue("a");
        two_secondVariable = new ConceptTextfieldComponent();
        three_firstAddConditionCheckbox = new Checkbox("that... (add condition)");
        three_firstAddConditionCheckbox.addClickListener(
                e -> addConditionBlock(true, three_firstAddConditionCheckbox.getValue()));
        firstRowComponentRuleLayout.setVerticalComponentAlignment(
                Alignment.END, three_firstAddConditionCheckbox);
        firstRowComponentRuleLayout.add(
                one_firstVariable,
                oneb_OptionalModifier,
                two_secondVariable,
                three_firstAddConditionCheckbox);

        secondRowComponentRuleLayout = new HorizontalLayout();
        four_firstModifier = new ComboBox<>("Modifier", ", then it must");
        four_firstModifier.setValue(", then it must");
        five_thirdVariable = new RelationTextfieldComponent();
        six_secondModifier =
                new ComboBox<String>("Modifier", Arrays.asList("this", "this a", "this an"));
        six_secondModifier.setValue("this");
        seven_fourthVariable = new ConceptTextfieldComponent();
        eight_secondAddConditionCheckbox = new Checkbox("that... (add condition)");
        eight_secondAddConditionCheckbox.addClickListener(
                e -> addConditionBlock(false, eight_secondAddConditionCheckbox.getValue()));
        secondRowComponentRuleLayout.setVerticalComponentAlignment(
                Alignment.END, eight_secondAddConditionCheckbox);
        secondRowComponentRuleLayout.add(
                four_firstModifier,
                five_thirdVariable,
                six_secondModifier,
                seven_fourthVariable,
                eight_secondAddConditionCheckbox);

        add(firstRowComponentRuleLayout, secondRowComponentRuleLayout);
    }

    private void addConditionBlock(Boolean conditionOne, Boolean showCondition) {
        if (conditionOne) {
            if (showCondition) {
                addComponentAtIndex(1, firstCondition);
            } else {
                remove(firstCondition);
            }
        } else {
            if (showCondition) {
                addComponentAtIndex(3, secondCondition);
            } else {
                remove(secondCondition);
            }
        }
    }

    @Override
    public String getString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(one_firstVariable.getValue() + " ");
        sBuilder.append(oneb_OptionalModifier.getValue() + " ");
        sBuilder.append(two_secondVariable.getValue());
        if (three_firstAddConditionCheckbox.getValue()) {
            sBuilder.append(" " + firstCondition.getString());
        }
        sBuilder.append(four_firstModifier.getValue() + " ");
        sBuilder.append(five_thirdVariable.getValue() + " ");
        sBuilder.append(six_secondModifier.getValue() + " ");
        sBuilder.append(seven_fourthVariable.getValue());
        if (eight_secondAddConditionCheckbox.getValue()) {
            sBuilder.append(" " + secondCondition.getString());
        }
        return sBuilder.toString();
    }
}
