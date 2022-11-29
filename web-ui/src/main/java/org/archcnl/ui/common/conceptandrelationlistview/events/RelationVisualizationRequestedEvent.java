package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.Component;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.input.visualization.PlantUmlTransformer;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.ui.common.visualization.AbstractVisualizationRequestedEvent;
import org.archcnl.ui.common.visualization.VisualizationWidget;

public class RelationVisualizationRequestedEvent extends AbstractVisualizationRequestedEvent {

    private static final long serialVersionUID = 3008663857447207603L;
    private final CustomRelation relation;

    public RelationVisualizationRequestedEvent(
            Component source, boolean fromClient, CustomRelation relation) {
        super(source, fromClient);
        this.relation = relation;
    }

    public CustomRelation getRelation() {
        return relation;
    }

    public void handleEvent(ConceptManager conceptManager, RelationManager relationManager) {
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        try {
            String code = transformer.transformToPlantUml(relation.getMapping().get());
            File diagramImage = writeDiagrammAsPng(code);
            new VisualizationWidget(diagramImage, code).open();
        } catch (MappingToUmlTranslationFailedException | IOException | NoSuchElementException e) {
            showErrorMessage(e);
        }
    }
}
