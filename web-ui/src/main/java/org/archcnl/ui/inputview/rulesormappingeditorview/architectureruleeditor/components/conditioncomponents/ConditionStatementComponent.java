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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.archcnl.ui.common.andtriplets.triplet.ConceptSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.PredicateSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.textfieldwidgets.VariableTextfieldWidget;

public class ConditionStatementComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private Label startLabelTextfield, endLabelTextfield;
    private PredicateSelectionComponent relationCombobox;
    private VariableTextfieldWidget variableTextfield;
    private ConceptSelectionComponent conceptCombobox;
    private ComboBox<String> modifierCombobox;
    private Checkbox andCheckbox;
    private ConditionStatementComponent newCondition;
    private HorizontalLayout conditionBox;
    private boolean usesConcept = true;
    private final String CHAR_REGEX = "[A-Za-z]+";
    private final String INTEGER_REGEX = "[+-]?[0-9]+";

    public ConditionStatementComponent() {
        setMargin(false);
        setPadding(false);

        initializeLayout();
    }

    private void initializeLayout() {
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

        createRelationCombobox();
        createConceptCombobox();
        createVariableTextfield();

        endLabelTextfield = new Label(")");
        conditionBox.setVerticalComponentAlignment(Alignment.END, endLabelTextfield);

        andCheckbox = new Checkbox("and...");
        conditionBox.setVerticalComponentAlignment(Alignment.END, andCheckbox);
        andCheckbox.addClickListener(e -> addCondition(andCheckbox.getValue()));

        conditionBox.add(
                startLabelTextfield,
                relationCombobox,
                modifierCombobox,
                conceptCombobox,
                endLabelTextfield,
                andCheckbox);
        add(conditionBox);
    }

    private void createRelationCombobox() {
        relationCombobox = new PredicateSelectionComponent();
        relationCombobox.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        relationCombobox.setLabel("Relation");
    }

    private void createConceptCombobox() {
        conceptCombobox = new ConceptSelectionComponent();
        conceptCombobox.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        conceptCombobox.setLabel("Concept");
    }

    private void createVariableTextfield() {
        Set<String> regexSet = new HashSet<>();
        regexSet.add(CHAR_REGEX);
        regexSet.add(INTEGER_REGEX);

        variableTextfield = new VariableTextfieldWidget(regexSet);
        variableTextfield.setLabel("Interger, String (or Concept)");
        variableTextfield.setPlaceholder("Interger, String (or Concept)");
    }

    private void firstComboboxListener(String value) {
        conditionBox.remove(conceptCombobox, variableTextfield, endLabelTextfield, andCheckbox);
        switch (value) {
            case "a":
            case "an":
            case "equal-to a":
            case "equal-to an":
                conditionBox.add(conceptCombobox, endLabelTextfield, andCheckbox);
                usesConcept = true;
                break;
            default:
                conditionBox.add(variableTextfield, endLabelTextfield, andCheckbox);
                usesConcept = false;
                break;
        }
    }

    private void addCondition(Boolean showCondition) {
        if (newCondition == null) {
            newCondition = new ConditionStatementComponent();
        }

        if (showCondition) {
            add(newCondition);
            return;
        }
        remove(newCondition);
    }

    @Override
    public String getRuleString() {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(startLabelTextfield.getText() + "");
        sBuilder.append(relationCombobox.getValue() + " ");
        sBuilder.append(modifierCombobox.getValue() + " ");

        if (usesConcept) {
            sBuilder.append(conceptCombobox.getValue());
        } else {
            sBuilder.append(variableTextfield.getValue());
        }

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
