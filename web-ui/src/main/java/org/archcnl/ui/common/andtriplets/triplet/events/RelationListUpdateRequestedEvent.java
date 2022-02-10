package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.ui.common.andtriplets.triplet.PredicateSelectionComponent;

public class RelationListUpdateRequestedEvent extends ComponentEvent<PredicateSelectionComponent> {

    private static final long serialVersionUID = 4804740257464327897L;

    public RelationListUpdateRequestedEvent(
            PredicateSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(List<Relation> relations) {
        String currentItem = getSource().getValue();
        getSource()
                .setItems(relations.stream().map(Relation::getName).collect(Collectors.toList()));
        getSource().setValue(currentItem);
    }
}
