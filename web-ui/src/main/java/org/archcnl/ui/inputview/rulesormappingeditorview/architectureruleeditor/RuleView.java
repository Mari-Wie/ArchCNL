package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;

public class RuleView extends HorizontalLayout {

    private static final long serialVersionUID = 4720863166018087691L;
    private ArchitectureRule rule;

    public RuleView(ArchitectureRule rule, int ruleIndex) {
        this.rule = rule;
        add(new Text(String.valueOf(ruleIndex) + "."));
        VerticalLayout vDivider = new VerticalLayout();
        vDivider.setWidth(20, Unit.PIXELS);
        vDivider.setHeight(100, Unit.PERCENTAGE);
        vDivider.setSpacing(true);
        vDivider.setMargin(true);
        add(vDivider);
        add(new Text(rule.toStringRepresentation()));
    }

    public ArchitectureRule getRule() {
        return rule;
    }
}
