package org.archcnl.domain.input.presets;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.presets.microservicearchitecture.ArchitecturalStyle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MicroserviceArchitectureTest {

    // other things needed in the test
    private static ArchitecturalStyle msa;
    private static RulesConceptsAndRelations model;

    @BeforeAll
    static void init() {

        // given
        msa = PresetsTestUtils.prepareMicroserviceArchitecture();

        // when
        msa.createRulesAndMappings();

        model = RulesConceptsAndRelations.getInstance();
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
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        // given + when is given from beforeAll-Method

        assertNotNull(model.getConceptManager().getConceptByName("Microservice"));

        // ServiceRegistry should be automatically added
        assertNotNull(model.getConceptManager().getConceptByName("ServiceRegistry"));

        // registerin Relation should be automatically added
        assertNotNull(model.getRelationManager().getRelationByName("registerin"));
    }

    /**
     * Test for the concepts and mappings of the DatabaseAccessAbstraction-Rule.<br>
     * Rule: Every Microservice must haveown DatabaseAccessAbstraction.
     */
    @Test
    void
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsDecentralizedPersistenceConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        // given + when is given from beforeAll-Method

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
    void
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsAPIGatewayConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        // given + when is given from beforeAll-Method

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
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsCircuitBreakerConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        // given + when is given from beforeAll-Method

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
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsApiMechanismConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        // given + when is given from beforeAll-Method

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
            givenMicroserviceArchitecture_whenRulesAndMappingsCreated_thenArchitectureContainsRuntimeEnvironmentConceptsAndRelations()
                    throws IOException, ConceptDoesNotExistException,
                            RelationDoesNotExistException {

        // given + when is given from beforeAll-Method

        assertNotNull(model.getConceptManager().getConceptByName("Microservice"));

        assertNotNull(model.getConceptManager().getConceptByName("RuntimeEnvironment"));

        // haveown Relation should be automatically added
        assertNotNull(model.getRelationManager().getRelationByName("useown"));
    }
}
