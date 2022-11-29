package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;

public class RelationVisualizationRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = 3008663857447207603L;
    private final CustomRelation relation;

    public RelationVisualizationRequestedEvent(
            Component source, boolean fromClient, CustomRelation relation) {
        super(source, fromClient);
        this.relation = relation;
    }

    public CustomRelation getRelation() {
        return relation;
    }

    public void handleEvent() {
        System.out.println("Relation vis");
    }
}
