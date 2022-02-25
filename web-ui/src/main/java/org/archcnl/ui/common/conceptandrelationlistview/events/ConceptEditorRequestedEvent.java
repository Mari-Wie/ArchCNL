package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;

public class ConceptEditorRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -605454386580616888L;
    private Optional<CustomConcept> concept;

    public ConceptEditorRequestedEvent(final Component source, final boolean fromClient) {
        super(source, fromClient);
        this.concept = Optional.empty();
    }

    public ConceptEditorRequestedEvent(
            final Component source, final boolean fromClient, final CustomConcept concept) {
        super(source, fromClient);
        this.concept = Optional.of(concept);
    }

    public Optional<CustomConcept> getConcept() {
        return concept;
    }
}
