package org.archcnl.ui.inputview.presets;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.ArchitecturalStyles;

/**
 * This is a Builder that creates ArchitecturalStyle Objects.<br>
 * These ArchiecturalStyle Objects are based on some JSON configuration-file,<br>
 * which is mapped to the model class.
 */
public class ArchitecturalStyleInputBuilder {

    public ArchitecturalStyle build(ArchitecturalStyles styleEnum) {
        switch (styleEnum) {
            case MICROSERVICE_ARCHITECTURE:
                ArchitecturalStyle micorserviceArchitecture = null;
                try {
                    micorserviceArchitecture =
                            new ObjectMapper()
                                    .readValue(
                                            new File(
                                                    "src/main/resources/MicroserviceArchitecture.json"),
                                            ArchitecturalStyle.class);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return micorserviceArchitecture;

            case LAYERED_ARCHITECTURE:
                ArchitecturalStyle layeredArchitecture = null;
                try {
                    layeredArchitecture =
                            new ObjectMapper()
                                    .readValue(
                                            new File("src/main/resources/LayeredArchitecture.json"),
                                            ArchitecturalStyle.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return layeredArchitecture;
            default:
                break;
        }
        return null;
    }
}
