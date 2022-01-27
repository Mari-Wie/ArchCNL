package org.archcnl.domain.input.presets;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.presets.microservicearchitecture.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.microservicearchitecture.MicroserviceArchitectureBuilder;
import org.archcnl.ui.common.TwoColumnGridEntry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MicroserviceArchitectureTest {

    // constants that will be used in helper-methods and test-methods

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

    // service registry

    // other things needed in the test
    private static ArchitecturalStyle msa;
    private static RulesConceptsAndRelations model;

    @BeforeAll
    static void init() {
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

        msa.createRulesAndMappings();

        model = RulesConceptsAndRelations.getInstance();
    }

    @AfterAll
    static void teardown() {
        msa = null;
    }

    // test methods

    /**
     * Test for the concepts and mappings of the ServiceRegistry-Rule.<br>
     * Rule: Every Microservice must registerin ServiceRegistry.
     */
    @Test
    void
            givenInput_whenArchitectureCreated_thenArchitectureContainsServiceRegistryRuleConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        assertNotNull(model.getConceptManager().getConceptByName("Microservice"));

        // ServiceRegistry should be automatically added
        assertNotNull(model.getConceptManager().getConceptByName("ServiceRegistry"));

        // registerin Relation should be automatically added
        assertNotNull(model.getRelationManager().getRelationByName("registerin"));
    }

    /**
     * Test for the concepts and mappings of the ServiceRegistry-Rule.<br>
     * Rule: Every Microservice must haveown DatabaseAccessAbstraction.
     */
    @Test
    void
            givenInput_whenArchitectureCreated_thenArchitectureContainsDecentralizedPersistenceConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        assertNotNull(model.getConceptManager().getConceptByName("Microservice"));

        // DatabaseAccessAbstraction should be automatically added
        assertNotNull(model.getConceptManager().getConceptByName("DatabaseAccessAbstraction"));

        // haveown Relation should be automatically added
        assertNotNull(model.getRelationManager().getRelationByName("haveown"));
    }

    /**
     * Test for the concepts and mappings of the API-Gateway-Rule.<br>
     * Rule: Every APIGateway must resideinpackage MicroserviceApp.
     */
    @Test
    void givenInput_whenArchitectureCreated_thenArchitectureContainsAPIGatewayConceptsAndRelations()
            throws IOException, ConceptDoesNotExistException, RelationDoesNotExistException {

        assertNotNull(model.getConceptManager().getConceptByName("Microservice"));

        // DatabaseAccessAbstraction should be automatically added
        assertNotNull(model.getConceptManager().getConceptByName("ApiGateway"));

        // haveown Relation should be automatically added
        assertNotNull(model.getRelationManager().getRelationByName("resideinpackage"));
    }

    /**
     * Test for the concepts and mappings of the CircuitBreaker-Rule.<br>
     * Rule: Every Microservice must use CircuitBreaker.
     */
    @Test
    void
            givenInput_whenArchitectureCreated_thenArchitectureContainsCircuitBreakerConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        assertNotNull(model.getConceptManager().getConceptByName("Microservice"));

        // DatabaseAccessAbstraction should be automatically added
        assertNotNull(model.getConceptManager().getConceptByName("CircuitBreaker"));

        // haveown Relation should be automatically added
        assertNotNull(model.getRelationManager().getRelationByName("use"));
    }

    /**
     * Test for the concepts and mappings of the CircuitBreaker-Rule.<br>
     * Rule: Every Microservice must haveown API.
     */
    @Test
    void
            givenInput_whenArchitectureCreated_thenArchitectureContainsApiMechanismConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        assertNotNull(model.getConceptManager().getConceptByName("Microservice"));

        assertNotNull(model.getConceptManager().getConceptByName("API"));

        // haveown Relation should be automatically added
        assertNotNull(model.getRelationManager().getRelationByName("haveown"));
    }

    /**
     * Test for the concepts and mappings of the CircuitBreaker-Rule.<br>
     * Rule: Every Microservice must useown RuntimeEnvironment.
     */
    @Test
    void
            givenInput_whenArchitectureCreated_thenArchitectureContainsRuntimeEnvironmentConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        assertNotNull(model.getConceptManager().getConceptByName("Microservice"));

        assertNotNull(model.getConceptManager().getConceptByName("RuntimeEnvironment"));

        // haveown Relation should be automatically added
        assertNotNull(model.getRelationManager().getRelationByName("useown"));
    }

    // helper-methods

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
