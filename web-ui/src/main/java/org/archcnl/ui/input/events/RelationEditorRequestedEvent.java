package org.archcnl.ui.input.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.CustomRelation;

public class RelationEditorRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = 2245617476018828750L;
    private Optional<CustomRelation> relation;

    public RelationEditorRequestedEvent(Component source, boolean fromClient) {
        super(source, fromClient);
        this.relation = Optional.empty();
    }

    public RelationEditorRequestedEvent(
            Component source, boolean fromClient, CustomRelation relation) {
        super(source, fromClient);
        this.relation = Optional.of(relation);
    }

    public Optional<CustomRelation> getRelation() {
        return relation;
    }
}
