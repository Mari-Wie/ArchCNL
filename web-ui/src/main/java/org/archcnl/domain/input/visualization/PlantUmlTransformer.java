package org.archcnl.domain.input.visualization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import net.sourceforge.plantuml.SourceStringReader;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.MappingFlattener;
import org.archcnl.domain.input.visualization.helpers.WrappingService;
import org.archcnl.domain.input.visualization.mapping.ColoredMapping;
import org.archcnl.domain.input.visualization.mapping.ColoredVariant;
import org.archcnl.domain.input.visualization.rules.RuleVisualizer;

public class PlantUmlTransformer {

    private static final String TEMP_FILE_PREFIX = "uml-";
    private static final String PNG_FILE_EXTENSION = ".png";
    private final ConceptManager conceptManager;
    private RelationManager relationManager;

    public PlantUmlTransformer(ConceptManager conceptManager, RelationManager relationManager) {
        this.conceptManager = conceptManager;
        this.relationManager = relationManager;
    }

    /*
    public File transformToDiagramPng() throws IOException, MappingToUmlTranslationFailedException {
        String source = transformToPlantUml();
        File tempFile = prepareTempFile();
        writeAsTemporaryPng(tempFile, source);
        return tempFile;
    }
    */

    public String transformToPlantUml(ArchitectureRule rule)
            throws MappingToUmlTranslationFailedException {
        RuleVisualizer visualizer =
                RuleVisualizer.createRuleVisualizer(rule, conceptManager, relationManager);
        return buildPlantUmlCode(visualizer);
    }

    public String transformToPlantUml(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        ColoredMapping coloredMapping = flattenAndRecreate(mapping);
        ConceptVisualizer visualizer =
                new ConceptVisualizer(
                        coloredMapping,
                        conceptManager,
                        relationManager,
                        Optional.empty(),
                        new HashSet<>());
        return buildPlantUmlCode(visualizer);
    }

    public String transformToPlantUml(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        ColoredMapping coloredMapping = flattenAndRecreate(mapping);
        RelationVisualizer visualizer =
                new RelationVisualizer(coloredMapping, conceptManager, relationManager);
        return buildPlantUmlCode(visualizer);
    }

    ColoredMapping flattenAndRecreate(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        ColoredMapping coloredMapping = ColoredMapping.fromMapping(mapping);
        MappingFlattener flattener = new MappingFlattener(coloredMapping);
        List<ColoredVariant> flattened = flattener.flattenCustomRelations();
        coloredMapping.setVariants(flattened);
        return coloredMapping;
    }

    private ColoredMapping flattenAndRecreate(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        ColoredMapping coloredMapping = ColoredMapping.fromMapping(mapping);
        List<ColoredVariant> wrappedVariants =
                WrappingService.wrapMapping(coloredMapping.getThenTriplet());
        coloredMapping.setVariants(wrappedVariants);

        MappingFlattener flattener = new MappingFlattener(coloredMapping);
        List<ColoredVariant> flattened = flattener.flattenCustomRelations();
        coloredMapping.setVariants(flattened);
        return coloredMapping;
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
        builder.append("@startuml\n");
        builder.append("title " + title + "\n");
        return builder.toString();
    }

    private String buildFooter() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n@enduml");
        return builder.toString();
    }

    private File prepareTempFile() throws IOException {
        File tempFile = File.createTempFile(TEMP_FILE_PREFIX, PNG_FILE_EXTENSION);
        tempFile.deleteOnExit();
        return tempFile;
    }

    private void writeAsTemporaryPng(File tempFile, String source) throws IOException {
        OutputStream pngStream = new FileOutputStream(tempFile);
        SourceStringReader reader = new SourceStringReader(source);
        reader.outputImage(pngStream);
    }
}
