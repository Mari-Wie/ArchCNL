package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.AddRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleEditorRequestedEvent;

public class NewArchitectureRuleView extends RulesOrMappingEditorView {

    private static final long serialVersionUID = -2966045554441002128L;
    private Button saveButton;
    private TextArea archRuleTextArea;

    public NewArchitectureRuleView(Optional<String> ruleString) {
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");
        setClassName("architecture-rules");

        saveButton = new Button("Save Rule", e -> saveRule());
        archRuleTextArea = new TextArea("Create new architecture rule ");
        archRuleTextArea.setWidthFull();
        ruleString.ifPresent(rule -> archRuleTextArea.setValue(rule));

        add(archRuleTextArea);
        add(saveButton);
    }

    private void saveRule() {
        if (!archRuleTextArea.isEmpty()) {
            fireEvent(new AddRuleButtonPressedEvent(this, true, archRuleTextArea.getValue()));
        }
        fireEvent(new RuleEditorRequestedEvent(this, true));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
