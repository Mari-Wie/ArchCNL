package org.archcnl.domain.input.presets;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.input.model.presets.microservicearchitecture.ArchitecturalStyle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MicroserviceArchitectureTest {

    // other things needed in the test
    private static ArchitecturalStyle msa;
    private static ConceptManager conceptManager;
    private static RelationManager relationManager;
    private static ArchitectureRuleManager ruleManager;

    @BeforeAll
    static void init() throws ConceptDoesNotExistException {

        // given
        msa = PresetsTestUtils.prepareMicroserviceArchitecture();

        // when

        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
        ruleManager = new ArchitectureRuleManager();

        msa.createRulesAndMappings(ruleManager, conceptManager, relationManager);
    }

    @AfterAll
    static void teardown() {
        msa = null;
    }

    /**
     * Test for the concepts and mappings of the ServiceRegistry-Rule.<br>
     * Rule: Every Microservice must registerin ServiceRegistry.
     */
    @Test
    void
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsServiceRegistryRuleConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException {

        // given + when is given from beforeAll-Method

        assertNotNull(conceptManager.getConceptByName("Microservice"));

        // ServiceRegistry should be automatically added
        assertNotNull(conceptManager.getConceptByName("ServiceRegistry"));

        // registerin Relation should be automatically added
        assertNotNull(relationManager.getRelationByName("registerin"));
    }

    /**
     * Test for the concepts and mappings of the DatabaseAccessAbstraction-Rule.<br>
     * Rule: Every Microservice must haveown DatabaseAccessAbstraction.
     */
    @Test
    void
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsDecentralizedPersistenceConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException {

        // given + when is given from beforeAll-Method

        assertNotNull(conceptManager.getConceptByName("Microservice"));

        // DatabaseAccessAbstraction should be automatically added
        assertNotNull(conceptManager.getConceptByName("DatabaseAccessAbstraction"));

        // haveown Relation should be automatically added
        assertNotNull(relationManager.getRelationByName("haveown"));
    }

    /**
     * Test for the concepts and mappings of the API-Gateway-Rule.<br>
     * Rule: Every APIGateway must resideinpackage MicroserviceApp.
     */
    @Test
    void
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsAPIGatewayConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException {

        // given + when is given from beforeAll-Method

        assertNotNull(conceptManager.getConceptByName("Microservice"));

        // DatabaseAccessAbstraction should be automatically added
        assertNotNull(conceptManager.getConceptByName("ApiGateway"));

        // haveown Relation should be automatically added
        assertNotNull(relationManager.getRelationByName("resideinpackage"));
    }

    /**
     * Test for the concepts and mappings of the CircuitBreaker-Rule.<br>
     * Rule: Every Microservice must use CircuitBreaker.
     */
    @Test
    void
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsCircuitBreakerConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException {

        // given + when is given from beforeAll-Method

        assertNotNull(conceptManager.getConceptByName("Microservice"));

        // DatabaseAccessAbstraction should be automatically added
        assertNotNull(conceptManager.getConceptByName("CircuitBreaker"));

        // haveown Relation should be automatically added
        assertNotNull(relationManager.getRelationByName("use"));
    }

    /**
     * Test for the concepts and mappings of the CircuitBreaker-Rule.<br>
     * Rule: Every Microservice must haveown API.
     */
    @Test
    void
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsApiMechanismConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException {

        // given + when is given from beforeAll-Method

        assertNotNull(conceptManager.getConceptByName("Microservice"));

        assertNotNull(conceptManager.getConceptByName("API"));

        // haveown Relation should be automatically added
        assertNotNull(relationManager.getRelationByName("haveown"));
    }

    /**
     * Test for the concepts and mappings of the CircuitBreaker-Rule.<br>
     * Rule: Every Microservice must useown RuntimeEnvironment.
     */
    @Test
    void
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsRuntimeEnvironmentConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException {

        // given + when is given from beforeAll-Method

        assertNotNull(conceptManager.getConceptByName("Microservice"));

        assertNotNull(conceptManager.getConceptByName("RuntimeEnvironment"));

        // haveown Relation should be automatically added
        assertNotNull(relationManager.getRelationByName("useown"));
    }
}
