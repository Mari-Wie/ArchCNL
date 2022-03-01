package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.subjectcomponents.SubjectComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents.VerbComponent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.DetermineVerbComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.SaveRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RulesWidgetRequestedEvent;

public class RuleCreatorView extends RulesOrMappingEditorView {

    private static final long serialVersionUID = -2966045554441002128L;
    private Button saveButton;
    private Button cancelButton;
    private TextArea archRuleTextArea;
    private SubjectComponent subject;
    private VerbComponent verb;
    private HorizontalLayout buttonsLayout;

    /**
     * Architecture rules are made up out of a subject component (Every/no/... concept) describing
     * the topic of the rule and a verb component (Can/must/...) describing the rule the subject
     * must follow. Refer to the architecture rule tree in the wiki for a visualization.
     */
    public RuleCreatorView(Optional<String> ruleString) {
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");
        setClassName("architecture-rules");

        initializeLayout();
        verb.determineVerbComponent(subject.getFirstModifier());
    }

    private void initializeLayout() {
        subject = new SubjectComponent();
        verb = new VerbComponent();
        buttonsLayout = new HorizontalLayout();

        subject.addListener(
                DetermineVerbComponentEvent.class,
                event -> verb.determineVerbComponent(event.getSource().getFirstModifier()));

        saveButton = new Button("Save Rule", e -> saveRule());
        cancelButton =
                new Button("Cancel", click -> fireEvent(new RulesWidgetRequestedEvent(this, true)));
        Checkbox activateExpertmode = new Checkbox("Activate Expertmode");
        activateExpertmode.addClickListener(e -> activateExpertMode(activateExpertmode.getValue()));
        buttonsLayout.setVerticalComponentAlignment(Alignment.CENTER, activateExpertmode);
        archRuleTextArea = new TextArea("Create new architecture rule");
        archRuleTextArea.setWidthFull();
        buttonsLayout.setPadding(true);
        buttonsLayout.add(saveButton, cancelButton, activateExpertmode);

        add(subject, verb, buttonsLayout);
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
                            this, true, subject.getString() + verb.getString()));
        }
        fireEvent(new RulesWidgetRequestedEvent(this, true));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
