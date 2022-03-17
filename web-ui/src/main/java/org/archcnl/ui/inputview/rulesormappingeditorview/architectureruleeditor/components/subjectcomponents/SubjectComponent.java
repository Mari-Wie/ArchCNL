package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.subjectcomponents;

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
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.conditioncomponents.ConditionStatementComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.DetermineStatementComponentEvent;

public class SubjectComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout subjectLayout;
    private ConditionStatementComponent newCondition;
    private ComboBox<String> one_DescriptorCombobox;
    private ConceptSelectionComponent two_ConceptCombobox;
    private Checkbox three_ConditionCheckbox;
    private boolean showFirstConcept = true, showCondition = false;
    private List<String> descriptorList =
            Arrays.asList(
                    "Every",
                    "Every a",
                    "Every an",
                    "Only",
                    "Only a",
                    "Only an",
                    "If",
                    "If a",
                    "If an",
                    "Nothing",
                    "No",
                    "No a",
                    "No an",
                    "Fact:");

    /**
     * The subject component is usually made out of a descriptor (Every, Only, If, No,...), a
     * concept and an optional condition chain.
     */
    public SubjectComponent() {
        this.add(new Label("Rule Subject"));
        this.getStyle().set("border", "1px solid black");
        this.setMargin(false);

        initializeLayout();
    }

    private void initializeLayout() {
        newCondition = new ConditionStatementComponent();
        newCondition.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        newCondition.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        subjectLayout = new HorizontalLayout();

        one_DescriptorCombobox = new ComboBox<String>("Modifier", descriptorList);
        one_DescriptorCombobox.setValue("Every");
        one_DescriptorCombobox.addValueChangeListener(
                e -> {
                    updateUI();
                });

        two_ConceptCombobox = createConceptCombobox();

        three_ConditionCheckbox = new Checkbox("that... (add condition)");
        three_ConditionCheckbox.addClickListener(e -> updateUI());

        subjectLayout.setVerticalComponentAlignment(Alignment.END, three_ConditionCheckbox);
        subjectLayout.add(one_DescriptorCombobox, two_ConceptCombobox, three_ConditionCheckbox);
        add(subjectLayout);
    }

    private ConceptSelectionComponent createConceptCombobox() {
        ConceptSelectionComponent conceptCombobox = new ConceptSelectionComponent();
        conceptCombobox.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        conceptCombobox.setLabel("Concept");
        return conceptCombobox;
    }

    private void updateUI() {
        subjectLayout.add(two_ConceptCombobox);
        showFirstConcept = true;

        String descriptorValue = one_DescriptorCombobox.getValue();
        switch (descriptorValue) {
            case "Nothing":
                subjectLayout.remove(two_ConceptCombobox);
                showFirstConcept = false;
            case "Fact:":
                subjectLayout.remove(three_ConditionCheckbox);
                showCondition(false);
                break;
            default:
                subjectLayout.add(three_ConditionCheckbox);
                showCondition(three_ConditionCheckbox.getValue());
                break;
        }
        fireEvent(new DetermineStatementComponentEvent(this, true));
    }

    private void showCondition(Boolean show) {
        if (show) {
            add(newCondition);
            showCondition = true;
            return;
        }
        remove(newCondition);
        showCondition = false;
    }

    public String getFirstModifierValue() {
        return one_DescriptorCombobox.getValue();
    }

    @Override
    public String getRuleString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(one_DescriptorCombobox.getValue() + " ");
        if (showFirstConcept) {
            sBuilder.append(two_ConceptCombobox.getValue() + " ");
        }
        if (showCondition) {
            sBuilder.append(newCondition.getRuleString());
        }
        return sBuilder.toString();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
