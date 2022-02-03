package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.ui.common.andtriplets.triplet.ObjectView;
import org.archcnl.ui.common.andtriplets.triplet.PredicateSelectionComponent;

public class PredicateSelectedEvent extends ComponentEvent<PredicateSelectionComponent> {

    private static final long serialVersionUID = 1L;
    private ObjectView objectView;

    public PredicateSelectedEvent(
            final PredicateSelectionComponent source, final boolean fromClient) {
        super(source, fromClient);
    }

    public void setObjectView(ObjectView objectView) {
        this.objectView = objectView;
    }

    public void handleEvent(final RelationManager relationManager) {
        final Optional<String> value = getSource().getSelectedItem();
        Optional<Relation> relation = Optional.empty();
        if (value.isPresent()) {
            relation = relationManager.getRelationByName(value.get());
            getSource().setInternalRelation(relation.get());
        } else {
            getSource().showErrorMessage("Relation does not exist");
        }
        objectView.predicateHasChanged(relation);
    }
}
