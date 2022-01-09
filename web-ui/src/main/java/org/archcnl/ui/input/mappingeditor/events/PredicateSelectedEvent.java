package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectView;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateComponent;

public class PredicateSelectedEvent extends ComponentEvent<PredicateComponent> {

    private static final long serialVersionUID = 1L;

    public PredicateSelectedEvent(PredicateComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(RelationManager relationManager, ObjectView objectView) {
        Optional<String> value = getSource().getSelectedItem();
        Optional<Relation> relation = null;
        if (value.isPresent()) {
            String name = value.get();
            relation = relationManager.getRelationByName(name);
        }
        objectView.predicateHasChanged(relation);
    }
}
