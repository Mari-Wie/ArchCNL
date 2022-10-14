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
        mapping = flattenCustomRelations(mapping);
        CustomConceptVisualizer visualizer =
                new CustomConceptVisualizer(
                        mapping, conceptManager, Optional.empty(), new HashSet<>());
        return buildPlantUmlCode(visualizer);
    }

    public String transformToPlantUml(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        mapping = flattenCustomRelations(mapping);
        CustomRelationVisualizer visualizer = new CustomRelationVisualizer(mapping, conceptManager);
        return buildPlantUmlCode(visualizer);
    }

    private ConceptMapping flattenCustomRelations(ConceptMapping mapping)
            throws MappingToUmlTranslationFailedException {
        MappingFlattener flattener =
                new MappingFlattener(
                        mapping.getWhenTriplets(),
                        mapping.getThenTriplet().getSubject(),
                        mapping.getThenTriplet().getObject());
        List<AndTriplets> flattenedAndTriplets = flattener.flatten();
        Variable thenSubject = mapping.getThenTriplet().getSubject();
        CustomConcept thisConcept = (CustomConcept) mapping.getThenTriplet().getObject();
        mapping = new ConceptMapping(thenSubject, flattenedAndTriplets, thisConcept);
        return mapping;
    }

    private RelationMapping flattenCustomRelations(RelationMapping mapping)
            throws MappingToUmlTranslationFailedException {
        MappingFlattener flattener =
                new MappingFlattener(
                        mapping.getWhenTriplets(),
                        mapping.getThenTriplet().getSubject(),
                        mapping.getThenTriplet().getObject());
        List<AndTriplets> flattenedAndTriplets = flattener.flatten();
        Triplet thenTriplet = mapping.getThenTriplet();
        mapping = new RelationMapping(thenTriplet, flattenedAndTriplets);
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
