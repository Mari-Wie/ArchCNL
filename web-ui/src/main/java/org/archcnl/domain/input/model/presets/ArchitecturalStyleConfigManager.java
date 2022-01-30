package org.archcnl.domain.input.model.presets;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 * This Configuration Manager builds Config-Objexts based on information that is provided in
 * specific json files.
 */
public class ArchitecturalStyleConfigManager {

    public ArchitecturalStyleConfig build(ArchitecturalStyle styleEnum) {
        switch (styleEnum) {
            case MICROSERVICE_ARCHITECTURE:
                ArchitecturalStyleConfig micorserviceArchitecture = null;
                try {
                    micorserviceArchitecture =
                            new ObjectMapper()
                                    .readValue(
                                            new File(
                                                    "src/main/resources/MicroserviceArchitecture.json"),
                                            ArchitecturalStyleConfig.class);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return micorserviceArchitecture;

            case LAYERED_ARCHITECTURE:
                ArchitecturalStyleConfig layeredArchitecture = null;
                try {
                    layeredArchitecture =
                            new ObjectMapper()
                                    .readValue(
                                            new File("src/main/resources/LayeredArchitecture.json"),
                                            ArchitecturalStyleConfig.class);
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
