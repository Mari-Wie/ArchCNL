package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;

public class RuleVisualizationRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = 6341211417340939066L;
    private final ArchitectureRule rule;

    public RuleVisualizationRequestedEvent(
            Component source, boolean fromClient, ArchitectureRule rule) {
        super(source, fromClient);
        this.rule = rule;
    }

    public ArchitectureRule getRule() {
        return rule;
    }

    public void handleEvent() {
        System.out.println("Rule vis");
    }
}
