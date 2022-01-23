package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.ui.common.andtriplets.triplet.ConceptSelectionComponent;

public class ConceptListUpdateRequestedEvent extends ComponentEvent<ConceptSelectionComponent> {

    private static final long serialVersionUID = -7285625733804607530L;

    public ConceptListUpdateRequestedEvent(ConceptSelectionComponent source, boolean fromClient) {
        super(source, fromClient);
    }

    public void handleEvent(List<Concept> concepts) {
        String currentItem = getSource().getValue();
        getSource().setItems(concepts.stream().map(Concept::getName).collect(Collectors.toList()));
        getSource().setValue(currentItem);
    }
}
