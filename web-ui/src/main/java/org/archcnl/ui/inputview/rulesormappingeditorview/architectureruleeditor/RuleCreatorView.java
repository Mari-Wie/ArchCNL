package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.subjectcomponents.SubjectComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents.StatementComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.DetermineVerbComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.SaveRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RulesWidgetRequestedEvent;

public class RuleCreatorView extends RulesOrMappingEditorView {

    private static final long serialVersionUID = -2966045554441002128L;
    private Button saveButton;
    private Button cancelButton;
    private TextArea archRuleTextArea;
    private SubjectComponent subject;
    private StatementComponent verb;
    private HorizontalLayout buttonsLayout;
    private Checkbox expertmodeCheckbox;

    /**
     * Architecture rules are made up out of a subject component (Every/no/... concept) describing
     * the topic of the rule and a verb component (Can/must/...) describing the rule the subject
     * must follow. Refer to the architecture rule tree in the documentation for a visualisation.
     */
    public RuleCreatorView(Optional<String> ruleString) {
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");
        setClassName("architecture-rules");

        initializeLayout(ruleString);
    }

    private void initializeLayout(Optional<String> ruleString) {
        subject = new SubjectComponent();
        subject.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        subject.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        subject.addListener(
                DetermineVerbComponentEvent.class,
                event -> verb.determineVerbComponent(event.getSource().getFirstModifierValue()));

        verb = new StatementComponent();
        verb.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        verb.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);

        buttonsLayout = new HorizontalLayout();
        saveButton = new Button("Save Rule", e -> saveRule());
        cancelButton =
                new Button("Cancel", click -> fireEvent(new RulesWidgetRequestedEvent(this, true)));
        expertmodeCheckbox = new Checkbox("Activate Expertmode");
        expertmodeCheckbox.addClickListener(e -> activateExpertMode(expertmodeCheckbox.getValue()));
        buttonsLayout.setVerticalComponentAlignment(Alignment.CENTER, expertmodeCheckbox);
        buttonsLayout.setPadding(true);
        buttonsLayout.add(saveButton, cancelButton, expertmodeCheckbox);

        archRuleTextArea = new TextArea("Create new architecture rule");
        archRuleTextArea.setWidthFull();

        add(subject, verb, buttonsLayout);

        ruleString.ifPresent(
                rule -> {
                    archRuleTextArea.setValue(rule);
                    expertmodeCheckbox.setValue(true);
                    activateExpertMode(true);
                });
    }

    private void activateExpertMode(Boolean show) {
        if (show) {
            add(archRuleTextArea);
        } else {
            remove(archRuleTextArea);
        }
    }

    private void saveRule() {
        if (!archRuleTextArea.isEmpty()) {
            fireEvent(new SaveRuleButtonPressedEvent(this, true, archRuleTextArea.getValue()));
        } else {
            fireEvent(
                    new SaveRuleButtonPressedEvent(
                            this, true, subject.getRuleString() + verb.getRuleString()));
        }
        fireEvent(new RulesWidgetRequestedEvent(this, true));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
