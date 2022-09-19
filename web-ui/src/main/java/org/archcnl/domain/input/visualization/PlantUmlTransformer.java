package org.archcnl.domain.input.visualization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;
import net.sourceforge.plantuml.SourceStringReader;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class PlantUmlTransformer {

    private static final String TEMP_FILE_PREFIX = "uml-";
    private static final String PNG_FILE_EXTENSION = ".png";
    private final ConceptManager conceptManager;
    private final AndTriplets andTriplets;
    private final Triplet thenTriplet;

    public PlantUmlTransformer(
            ConceptManager conceptManager, AndTriplets andTriplets, Triplet thenTriplet) {
        this.conceptManager = conceptManager;
        this.andTriplets = andTriplets;
        this.thenTriplet = thenTriplet;
    }

    public File transformToDiagramPng() throws IOException, MappingToUmlTranslationFailedException {
        String source = transformToPlantUml();
        File tempFile = prepareTempFile();
        writeAsTemporaryPng(tempFile, source);
        return tempFile;
    }

    public String transformToPlantUml() throws MappingToUmlTranslationFailedException {
        MappingTranslator translator = new MappingTranslator(andTriplets, thenTriplet);
        List<PlantUmlPart> umlElements = translator.translateToPlantUmlModel(conceptManager);
        return buildPlantUmlCode(umlElements);
    }

    private String buildPlantUmlCode(List<PlantUmlPart> umlElements) {
        String title = thenTriplet.transformToGui();
        StringBuilder builder = new StringBuilder();
        builder.append(buildHeader(title));
        builder.append(
                umlElements.stream()
                        .map(PlantUmlPart::buildPlantUmlCode)
                        .collect(Collectors.joining("\n")));
        builder.append(buildFooter());
        return builder.toString();
    }

    private String buildHeader(String title) {
        StringBuilder builder = new StringBuilder();
        builder.append("@startuml\n");
        // builder.append("title " + title + "\n");
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
