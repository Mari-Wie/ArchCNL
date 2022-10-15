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
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.helpers.MappingFlattener;
import org.archcnl.domain.input.visualization.helpers.WrappingService;

public class PlantUmlTransformer {

    private static final String TEMP_FILE_PREFIX = "uml-";
    private static final String PNG_FILE_EXTENSION = ".png";
    private final ConceptManager conceptManager;

    public PlantUmlTransformer(ConceptManager conceptManager) {
        this.conceptManager = conceptManager;
    }

    /*
    public File transformToDiagramPng() throws IOException, MappingToUmlTranslationFailedException {
        String source = transformToPlantUml();
        File tempFile = prepareTempFile();
        writeAsTemporaryPng(tempFile, source);
        return tempFile;
    }
    */

    public String transformToPlantUml(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        mapping = flattenAndRecreate(mapping);
        CustomConceptVisualizer visualizer =
                new CustomConceptVisualizer(
                        mapping, conceptManager, Optional.empty(), new HashSet<>());
        return buildPlantUmlCode(visualizer);
    }

    public String transformToPlantUml(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        mapping = flattenAndRecreate(mapping);
        CustomRelationVisualizer visualizer = new CustomRelationVisualizer(mapping, conceptManager);
        return buildPlantUmlCode(visualizer);
    }

    private ConceptMapping flattenAndRecreate(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        List<AndTriplets> wrappedAndflattened = MappingFlattener.flattenCustomRelations(mapping);
        List<AndTriplets> unwrapped = WrappingService.unwrapConceptMapping(wrappedAndflattened);
        Variable thenSubject = mapping.getThenTriplet().getSubject();
        CustomConcept thisConcept = (CustomConcept) mapping.getThenTriplet().getObject();
        mapping = new ConceptMapping(thenSubject, unwrapped, thisConcept);
        return mapping;
    }

    private RelationMapping flattenAndRecreate(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        List<AndTriplets> flattened = MappingFlattener.flattenCustomRelations(mapping);
        Triplet thenTriplet = mapping.getThenTriplet();
        mapping = new RelationMapping(thenTriplet, flattened);
        return mapping;
    }

    private String buildPlantUmlCode(MappingVisualizer visualizer) {
        StringBuilder builder = new StringBuilder();
        builder.append(buildHeader(visualizer.getMappingName()));
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
