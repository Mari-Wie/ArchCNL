package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.RuleCreatorView;

public class SaveRuleButtonPressedEvent extends ComponentEvent<RuleCreatorView> {

    private static final long serialVersionUID = 366316698961954929L;
    private String ruleString;

    public SaveRuleButtonPressedEvent(
            RuleCreatorView source, boolean fromClient, String ruleString) {
        super(source, fromClient);
        this.ruleString = ruleString;
    }

    public String getRuleString() {
        return ruleString;
    }
}
