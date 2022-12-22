package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.Component;
import java.io.File;
import java.io.IOException;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.visualization.PlantUmlTransformer;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.ui.common.visualization.AbstractVisualizationRequestedEvent;
import org.archcnl.ui.common.visualization.VisualizationWidget;

public class RuleVisualizationRequestedEvent extends AbstractVisualizationRequestedEvent {

    private static final long serialVersionUID = 6341211417340939066L;
    private final ArchitectureRule rule;

    public RuleVisualizationRequestedEvent(
            Component source, boolean fromClient, ArchitectureRule rule) {
        super(source, fromClient);
        this.rule = rule;
    }

    public ArchitectureRule getRule() {
        return rule;
    }

    public void handleEvent(ConceptManager conceptManager, RelationManager relationManager) {
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        try {
            String code = transformer.transformToPlantUml(rule);
            File diagramImage = writeDiagrammAsPng(code);
            new VisualizationWidget(diagramImage, code).open();
        } catch (MappingToUmlTranslationFailedException | IOException e) {
            showErrorMessage(e);
        }
    }
}
