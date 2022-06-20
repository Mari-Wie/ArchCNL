package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import java.time.LocalDate;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.RuleCreatorView;

public class SaveRuleButtonPressedEvent extends ComponentEvent<RuleCreatorView> {

    private static final long serialVersionUID = 366316698961954929L;
    private String ruleString;
    private LocalDate validFrom;
    private LocalDate validUntil;

    public SaveRuleButtonPressedEvent(
            RuleCreatorView source,
            boolean fromClient,
            String ruleString,
            LocalDate validFrom,
            LocalDate validUntil) {
        super(source, fromClient);
        this.ruleString = ruleString;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public String getRuleString() {
        return ruleString;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }
}
