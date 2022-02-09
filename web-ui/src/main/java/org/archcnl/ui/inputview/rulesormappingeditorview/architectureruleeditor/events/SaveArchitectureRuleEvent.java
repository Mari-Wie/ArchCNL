package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.NewArchitectureRuleView;

import com.vaadin.flow.component.ComponentEvent;

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
