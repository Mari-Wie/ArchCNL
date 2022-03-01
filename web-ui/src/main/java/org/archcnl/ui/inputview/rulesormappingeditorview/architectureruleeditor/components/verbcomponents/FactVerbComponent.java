package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.Arrays;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.textfieldwidgets.ConceptTextfieldWidget;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.textfieldwidgets.RelationTextfieldWidget;

public class FactVerbComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout componentRuleLayout;
    private ComboBox<String> one_firstModifier, three_secondModifier;
    private ConceptTextfieldWidget twoA_firstVariable_Concept, four_thirdVariable;
    private RelationTextfieldWidget twoB_firstVariable_Relation;
    private boolean isInUpperBranch = false;

    public FactVerbComponent() {
        this.setMargin(false);
        this.setPadding(false);

        initializeLayout();
    }

    private void initializeLayout() {
        componentRuleLayout = new HorizontalLayout();
        one_firstModifier = new ComboBox<>("Modifier", Arrays.asList("is a", "is an", " "));
        one_firstModifier.setValue(" ");
        one_firstModifier.addValueChangeListener(
                e -> {
                    updateUI();
                });

        twoA_firstVariable_Concept = new ConceptTextfieldWidget();
        twoB_firstVariable_Relation = new RelationTextfieldWidget();

        three_secondModifier = new ComboBox<>("Modifier", Arrays.asList("equal-to", " "));
        three_secondModifier.setValue("equal-to");
        three_secondModifier.addValueChangeListener(
                e -> {
                    updateUI();
                });

        four_thirdVariable = new ConceptTextfieldWidget();
        componentRuleLayout.add(
                one_firstModifier,
                twoB_firstVariable_Relation,
                three_secondModifier,
                four_thirdVariable);
        add(componentRuleLayout);
        updateUI();
    }

    private void updateUI() {
        componentRuleLayout.removeAll();

        if (!one_firstModifier.getValue().equals(" ")) {
            isInUpperBranch = true;
            componentRuleLayout.add(one_firstModifier, twoA_firstVariable_Concept);
            return;
        }

        isInUpperBranch = false;
        componentRuleLayout.add(
                one_firstModifier,
                twoB_firstVariable_Relation,
                three_secondModifier,
                four_thirdVariable);

        if (three_secondModifier.getValue().equals("equal-to")) {
            four_thirdVariable.setPlaceholder("+/- [0-9] / String");
            four_thirdVariable.setLabel("Integer or String");
            return;
        }

        four_thirdVariable.setPlaceholder("Concept");
        four_thirdVariable.setLabel("Concept");
    }

    @Override
    public String getRuleString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(one_firstModifier.getValue() + " ");
        if (isInUpperBranch) {
            sBuilder.append(twoA_firstVariable_Concept.getValue() + " ");
        } else {
            sBuilder.append(twoB_firstVariable_Relation.getValue() + " ");
            sBuilder.append(three_secondModifier.getValue() + " ");
            sBuilder.append(four_thirdVariable.getValue());
        }
        return sBuilder.toString();
    }
}
