package org.archcnl.domain.input.visualization;

import java.util.HashSet;
import java.util.Optional;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.NamePicker;
import org.archcnl.domain.input.visualization.rules.RuleVisualizer;

public class PlantUmlTransformer {

    private final ConceptManager conceptManager;
    private RelationManager relationManager;

    public PlantUmlTransformer(ConceptManager conceptManager, RelationManager relationManager) {
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
    }

    public String transformToPlantUml(ArchitectureRule rule)
            throws MappingToUmlTranslationFailedException {
        NamePicker.reset();
        RuleVisualizer visualizer =
                RuleVisualizer.createRuleVisualizer(rule, conceptManager, relationManager);
        return buildPlantUmlCode(visualizer);
    }

    public String transformToPlantUml(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        NamePicker.reset();
        ConceptVisualizer visualizer =
                new ConceptVisualizer(
                        mapping,
                        conceptManager,
                        relationManager,
                        Optional.empty(),
                        new HashSet<>(),
                        ColorState.NEUTRAL);
        visualizer.enableIsTopLevelConcept();
        return buildPlantUmlCode(visualizer);
    }

    public String transformToPlantUml(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        NamePicker.reset();
        RelationVisualizer visualizer =
                new RelationVisualizer(mapping, conceptManager, relationManager);
        return buildPlantUmlCode(visualizer);
    }

    private String buildPlantUmlCode(Visualizer visualizer) {
        StringBuilder builder = new StringBuilder();
        builder.append(buildHeader(visualizer.getName()));
        builder.append(visualizer.buildPlantUmlCode());
        builder.append(buildFooter());
        return builder.toString();
    }

    private String buildHeader(String title) {
        StringBuilder builder = new StringBuilder();
        builder.append("@startuml " + title + "\n");
        builder.append("title " + title + "\n");
        return builder.toString();
    }

    private String buildFooter() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n@enduml");
        return builder.toString();
    }
}
