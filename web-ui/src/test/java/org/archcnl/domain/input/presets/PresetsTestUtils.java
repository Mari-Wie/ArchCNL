package org.archcnl.domain.input.presets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.archcnl.domain.input.model.presets.microservicearchitecture.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.microservicearchitecture.MicroserviceArchitectureBuilder;
import org.archcnl.ui.common.TwoColumnGridEntry;

public class PresetsTestUtils {

    private static ArchitecturalStyle msa;

    // architecture rules
    private static final String RUNTIME_ENV_RULE =
            "Every Microservice must useown RuntimeEnvironment.";
    private static final String API_GATEWAY_RULE =
            "Every ApiGateway must resideinpackage MicroserviceApp.";
    private static final String API_RULE = "Every Microservice must haveown API.";
    private static final String CIRCUIT_BREAKER_RULE =
            "Every Microservice must use CircuitBreaker.";
    private static final String DECENTRALIZED_PERSISTENCE_RULE =
            "Every Microservice must haveown DatabaseAccessAbstraction.";
    private static final String SERVICE_REGISTRY_RULE =
            "Every Microservice must registerin ServiceRegistry.";

    // first microservice
    private static final String MICROSERVICE_ONE_NAME = "MicroserviceOne";
    private static final String MICROSERVICE_ONE_MAIN_CLASS_NAME = "MicroserviceOneApplication";

    // second microservice
    private static final String MICROSERVICE_TWO_NAME = "MicroserviceTwo";
    private static final String MICROSERVICE_TWO_MAIN_CLASS_NAME = "MicroserviceTwoApplication";

    // Service Registry
    private static final String SERVICE_REGISTRY_CLASS_NAME = "RegistryApplication";
    private static final String SERVICE_REGISTRY_IMPORT_NAME = "EnableDiscoveryClient";

    // DB Access Abstraction
    private static final String DATABASE_ACCESS_ABSTRACTION_NAME = "Repository";
    private static final String DATABASE_ACCESS_ABSTRACTION_ENDING = "Repository";

    // Api Gateway
    private static final String API_GATEWAY_CLASS_NAME = "GatewayApplication";

    // package info
    private static final String MS_APP_PACKAGE_STRUCTURE = "com.example";

    // circuit breaker info
    private static final String CIRCUIT_BREAKER_IMPORT_CLASS_NAME = "EnableCircuitBreaker";

    // api mechanisms
    private static final String API_MEACHISM_NAME = "RestController";
    private static final String API_MEACHISM_NAME_ENDING = "RestController";

    public static ArchitecturalStyle prepareMicroserviceArchitecture() {

        Set<TwoColumnGridEntry> microservices = createMicroservices();
        Set<TwoColumnGridEntry> apiMechanisms = createApiMechanisms();
        Set<TwoColumnGridEntry> dbAccessAbstractions = createdbAccessAbstractions();

        msa =
                new MicroserviceArchitectureBuilder()
                        .withApiGatewayPackageName(API_GATEWAY_CLASS_NAME)
                        .withMsAppPackageStructure(MS_APP_PACKAGE_STRUCTURE)
                        .withCircuitBreakerImportClassName(CIRCUIT_BREAKER_IMPORT_CLASS_NAME)
                        .withServiceRegistryClassName(SERVICE_REGISTRY_CLASS_NAME)
                        .withRegistryImportName(SERVICE_REGISTRY_IMPORT_NAME)
                        .withMicroservices(microservices)
                        .withApiMechanisms(apiMechanisms)
                        .withDbAccessAbstractions(dbAccessAbstractions)
                        .build();

        return msa;
    }

    public static List<String> getMicroserviceArchitectureRules() {
        List<String> knownRules = new ArrayList<String>();

        knownRules.add(SERVICE_REGISTRY_RULE);
        knownRules.add(DECENTRALIZED_PERSISTENCE_RULE);
        knownRules.add(CIRCUIT_BREAKER_RULE);
        knownRules.add(API_RULE);
        knownRules.add(API_GATEWAY_RULE);
        knownRules.add(RUNTIME_ENV_RULE);

        return knownRules;
    }

    private static Set<TwoColumnGridEntry> createMicroservices() {
        Set<TwoColumnGridEntry> services = new HashSet<TwoColumnGridEntry>();
        TwoColumnGridEntry microserviceOne =
                new TwoColumnGridEntry(MICROSERVICE_ONE_NAME, MICROSERVICE_ONE_MAIN_CLASS_NAME);
        TwoColumnGridEntry microserviceTwo =
                new TwoColumnGridEntry(MICROSERVICE_TWO_NAME, MICROSERVICE_TWO_MAIN_CLASS_NAME);

        services.add(microserviceOne);
        services.add(microserviceTwo);
        return services;
    }

    private static Set<TwoColumnGridEntry> createApiMechanisms() {
        Set<TwoColumnGridEntry> mechanisms = new HashSet<TwoColumnGridEntry>();
        TwoColumnGridEntry mechanismOne =
                new TwoColumnGridEntry(API_MEACHISM_NAME, API_MEACHISM_NAME_ENDING);

        mechanisms.add(mechanismOne);

        return mechanisms;
    }

    private static Set<TwoColumnGridEntry> createdbAccessAbstractions() {
        Set<TwoColumnGridEntry> dbAbstractions = new HashSet<TwoColumnGridEntry>();
        TwoColumnGridEntry dbAccessAbstraction =
                new TwoColumnGridEntry(
                        DATABASE_ACCESS_ABSTRACTION_NAME, DATABASE_ACCESS_ABSTRACTION_ENDING);

        dbAbstractions.add(dbAccessAbstraction);

        return dbAbstractions;
    }
}
