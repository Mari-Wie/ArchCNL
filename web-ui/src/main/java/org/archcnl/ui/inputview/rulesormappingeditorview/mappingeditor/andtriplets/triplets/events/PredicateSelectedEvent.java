package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.ObjectView;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.PredicateComponent;

public class PredicateSelectedEvent extends ComponentEvent<PredicateComponent> {

    private static final long serialVersionUID = -5512289455282443156L;

    public PredicateSelectedEvent(PredicateComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(RelationManager relationManager, ObjectView objectView) {
        Optional<String> value = getSource().getSelectedItem();
        Relation relation = null;
        if (value.isPresent()) {
            String name = value.get();
            try {
                relation = relationManager.getRelationByName(name);
            } catch (RelationDoesNotExistException e) {
                getSource().showErrorMessage("Relation does not exist");
            }
        }
        objectView.predicateHasChanged(Optional.ofNullable(relation));
    }
}
