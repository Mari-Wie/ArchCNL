package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.Component;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.input.visualization.PlantUmlTransformer;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.ui.common.visualization.AbstractVisualizationRequestedEvent;
import org.archcnl.ui.common.visualization.VisualizationWidget;

public class ConceptVisualizationRequestedEvent extends AbstractVisualizationRequestedEvent {

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

    public void handleEvent(ConceptManager conceptManager, RelationManager relationManager) {
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        try {
            String code = transformer.transformToPlantUml(concept.getMapping().get());
            File diagramImage = writeDiagrammAsPng(code);
            new VisualizationWidget(diagramImage, code).open();
        } catch (MappingToUmlTranslationFailedException | IOException | NoSuchElementException e) {
            showErrorMessage(e);
        }
    }
}
