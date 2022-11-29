package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;

public class ConceptVisualizationRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = 240483003547721971L;
    private final CustomConcept concept;

    public ConceptVisualizationRequestedEvent(
            Component source, boolean fromClient, CustomConcept concept) {
        super(source, fromClient);
        this.concept = concept;
    }

    public CustomConcept getConcept() {
        return concept;
    }

    public void handleEvent() {
        System.out.println("Concept vis");
    }
}
