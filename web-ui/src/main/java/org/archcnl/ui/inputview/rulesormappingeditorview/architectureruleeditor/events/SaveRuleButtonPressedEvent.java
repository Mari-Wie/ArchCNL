package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.NewArchitectureRuleView;

public class SaveRuleButtonPressedEvent extends ComponentEvent<NewArchitectureRuleView> {

    private static final long serialVersionUID = 366316698961954929L;
    private String ruleString;

    public SaveRuleButtonPressedEvent(
            NewArchitectureRuleView source, boolean fromClient, String ruleString) {
        super(source, fromClient);
        this.ruleString = ruleString;
    }

    public String getRuleString() {
        return ruleString;
    }
}
