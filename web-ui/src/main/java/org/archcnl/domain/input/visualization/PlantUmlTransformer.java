package org.archcnl.domain.input.visualization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import net.sourceforge.plantuml.SourceStringReader;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;

public class PlantUmlTransformer {

    private static final String TEMP_FILE_PREFIX = "uml-";
    private static final String PNG_FILE_EXTENSION = ".png";

    public File transformToDiagramPng(AndTriplets andTriplets, Triplet thenTriplet)
            throws IOException {
        String source = transformToPlantUml(andTriplets, thenTriplet);
        File tempFile = prepareTempFile();
        writeAsTemporaryPng(tempFile, source);
        return tempFile;
    }

    private String transformToPlantUml(AndTriplets andTriplets, Triplet thenTriplet) {
        String title = "title";
        StringBuilder builder = new StringBuilder();
        builder.append("@startuml");
        builder.append("title " + title);

        builder.append("@enduml");
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
