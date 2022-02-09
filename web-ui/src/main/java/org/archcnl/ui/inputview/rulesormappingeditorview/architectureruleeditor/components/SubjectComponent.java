package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components;

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

import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.DetermineVerbComponentEvent;

public class SubjectComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout subjectLayout;
    private ConditionComponent newCondition;
    private ComboBox<String> one_DescriptorCombobox;
    private ConceptTextfieldComponent two_FirstConcept;
    private Checkbox three_ConditionCheckbox;
    private boolean showFirstConcept = true;

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
        newCondition = new ConditionComponent();

        subjectLayout = new HorizontalLayout();
        List<String> firstStatements =
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

        one_DescriptorCombobox = new ComboBox<String>("Modifier", firstStatements);
        one_DescriptorCombobox.setValue("Every");
        one_DescriptorCombobox.addValueChangeListener(
                e -> {
                    updateUI(one_DescriptorCombobox.getValue());
                });
        two_FirstConcept = new ConceptTextfieldComponent();
        two_FirstConcept.setLabel("Concept");
        three_ConditionCheckbox = new Checkbox("that... (add condition)");
        three_ConditionCheckbox.addClickListener(
                e -> addCondition(three_ConditionCheckbox.getValue()));
        subjectLayout.setVerticalComponentAlignment(Alignment.END, three_ConditionCheckbox);
        subjectLayout.add(one_DescriptorCombobox, two_FirstConcept, three_ConditionCheckbox);

        add(subjectLayout);
    }

    private void updateUI(String value) {
        if (value.equals("Nothing")) {
            subjectLayout.remove(two_FirstConcept, three_ConditionCheckbox);
            showFirstConcept = false;
        } else if (value.equals("Fact:")) {
            subjectLayout.remove(three_ConditionCheckbox);
        } else {
            subjectLayout.add(two_FirstConcept, three_ConditionCheckbox);
            showFirstConcept = true;
        }
        fireEvent(new DetermineVerbComponentEvent(this, true));
    }

    private void addCondition(Boolean showCondition) {
        if (showCondition) {
            add(newCondition);
        } else {
            remove(newCondition);
        }
    }

    public String getFirstModifier() {
        return one_DescriptorCombobox.getValue();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    @Override
    public String getString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(one_DescriptorCombobox.getValue() + " ");
        if (showFirstConcept) {
            sBuilder.append(two_FirstConcept.getValue() + " ");
            if (three_ConditionCheckbox.getValue()) {
                sBuilder.append(newCondition.getString());
            }
        }
        return sBuilder.toString();
    }
}
