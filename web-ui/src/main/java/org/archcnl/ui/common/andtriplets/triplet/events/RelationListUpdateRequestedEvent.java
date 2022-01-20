package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.common.Relation;
import org.archcnl.ui.common.andtriplets.triplet.PredicateComponent;

public class RelationListUpdateRequestedEvent extends ComponentEvent<PredicateComponent> {

    private static final long serialVersionUID = 4804740257464327897L;

    public RelationListUpdateRequestedEvent(PredicateComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(List<Relation> relations) {
        String currentItem = getSource().getValue();
        getSource()
                .setItems(relations.stream().map(Relation::getName).collect(Collectors.toList()));
        getSource().setValue(currentItem);
    }
}
