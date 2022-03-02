package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.conditioncomponents;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.Arrays;
import java.util.List;

import org.archcnl.ui.common.andtriplets.triplet.ConceptSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.PredicateSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.textfieldwidgets.ConceptTextfieldWidget;

public class ConditionStatement extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private Label startLabelTextfield, endLabelTextfield;
    private PredicateSelectionComponent relationVariable;
    private ConceptTextfieldWidget freeTextVariable;
    private ConceptSelectionComponent conceptVariable;
    private ComboBox<String> modifierCombobox;
    private Checkbox andCheckbox;
    private ConditionStatement newCondition;
    private HorizontalLayout conditionBox;

    public ConditionStatement() {
        setMargin(false);
        setPadding(false);
        conditionBox = new HorizontalLayout();
        conditionBox.setMargin(false);

        startLabelTextfield = new Label("that(");
        conditionBox.setVerticalComponentAlignment(Alignment.END, startLabelTextfield);

        List<String> firstStatements =
                Arrays.asList(" ", "a", "an", "equal-to", "equal-to a", "equal-to an");
        modifierCombobox = new ComboBox<String>("Modifier", firstStatements);
        modifierCombobox.setValue("a");
        modifierCombobox.addValueChangeListener(
                e -> {
                    firstComboboxListener(modifierCombobox.getValue());
                });

        createRelationVariable();
        createConceptVariable();
        createFreeTextVariable();

        endLabelTextfield = new Label(")");
        conditionBox.setVerticalComponentAlignment(Alignment.END, endLabelTextfield);

        andCheckbox = new Checkbox("and...");
        conditionBox.setVerticalComponentAlignment(Alignment.END, andCheckbox);
        andCheckbox.addClickListener(e -> addCondition(andCheckbox.getValue()));

        conditionBox.add(
                startLabelTextfield,
                relationVariable,
                modifierCombobox,
                conceptVariable,
                endLabelTextfield,
                andCheckbox);
        add(conditionBox);
    }

    private void createRelationVariable() {
        relationVariable = new PredicateSelectionComponent();
        relationVariable.setLabel("Relation");
        relationVariable.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
    }
    
    private void createFreeTextVariable() {
        freeTextVariable = new ConceptTextfieldWidget();
        freeTextVariable.setLabel("Concept, Interger or String");
        freeTextVariable.setPlaceholder("Concept, Interger or String");
    }
    
    private void createConceptVariable() {
    	conceptVariable = new ConceptSelectionComponent();
    	conceptVariable.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
    	conceptVariable.setLabel("Concept");
    }

    private void firstComboboxListener(String value) {
    	switch (value) {
		case "a":
		case "an":
		case "equal-to a":
		case "equal-to an":
			conditionBox.replace(freeTextVariable, conceptVariable);
			break;
		default:
			conditionBox.replace(conceptVariable, freeTextVariable);
			break;
		}
    }

    private void addCondition(Boolean showCondition) {
        if (newCondition == null) {
            newCondition = new ConditionStatement();
        }
        if (showCondition) {
            add(newCondition);
        } else {
            remove(newCondition);
        }
    }

    @Override
    public String getRuleString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(startLabelTextfield.getText() + "");
        sBuilder.append(relationVariable.getValue() + " ");
        sBuilder.append(modifierCombobox.getValue() + " ");
        sBuilder.append(freeTextVariable.getValue());
        sBuilder.append(endLabelTextfield.getText() + " ");
        if (andCheckbox.getValue()) {
            sBuilder.append("and " + newCondition.getRuleString());
        }

        return sBuilder.toString();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
