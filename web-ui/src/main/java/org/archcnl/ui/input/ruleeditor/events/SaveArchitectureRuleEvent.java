package org.archcnl.ui.input.ruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.ruleeditor.NewArchitectureRuleView;

public class SaveArchitectureRuleEvent extends ComponentEvent<NewArchitectureRuleView> {

    private static final long serialVersionUID = 1L;
    private String architectureRule;

    public SaveArchitectureRuleEvent(
            NewArchitectureRuleView source, boolean fromClient, String architectureRule) {
        super(source, fromClient);
        this.architectureRule = architectureRule;
    }

    public String getArchitectureRule() {
        return architectureRule;
    }
}
