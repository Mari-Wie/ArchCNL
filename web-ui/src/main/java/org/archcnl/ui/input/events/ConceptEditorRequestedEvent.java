package org.archcnl.ui.input.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.CustomConcept;

public class ConceptEditorRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -605454386580616888L;
    private Optional<CustomConcept> concept;

    public ConceptEditorRequestedEvent(Component source, boolean fromClient) {
        super(source, fromClient);
        this.concept = Optional.empty();
    }

    public ConceptEditorRequestedEvent(
            Component source, boolean fromClient, CustomConcept concept) {
        super(source, fromClient);
        this.concept = Optional.of(concept);
    }

    public Optional<CustomConcept> getConcept() {
        return concept;
    }
}
