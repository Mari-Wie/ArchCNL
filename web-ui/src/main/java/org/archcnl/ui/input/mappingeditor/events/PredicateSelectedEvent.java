package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectView;
import org.archcnl.ui.input.mappingeditor.triplet.PredicateComponent;

public class PredicateSelectedEvent extends ComponentEvent<PredicateComponent> {

    private static final long serialVersionUID = 1L;

    public PredicateSelectedEvent(final PredicateComponent source, final boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(final RelationManager relationManager, final ObjectView objectView) {
        final Optional<String> value = getSource().getSelectedItem();
        Optional<Relation> relation = null;
        if (value.isPresent()) {
            relation = relationManager.getRelationByName(value.get());
        } else {
            getSource().showErrorMessage("Relation does not exist");
        }
        objectView.predicateHasChanged(relation);
    }
}
