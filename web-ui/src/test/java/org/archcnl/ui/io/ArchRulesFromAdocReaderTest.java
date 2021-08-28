package org.archcnl.ui.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.archcnl.input.datatypes.RulesAndMappings;
import org.archcnl.input.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.input.datatypes.mappings.ConceptManager;
import org.archcnl.input.datatypes.mappings.Mapping;
import org.archcnl.input.datatypes.mappings.RelationManager;
import org.archcnl.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.input.exceptions.InvalidVariableNameException;
import org.archcnl.input.exceptions.RecursiveRelationException;
import org.archcnl.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.input.exceptions.RelationDoesNotExistException;
import org.archcnl.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.input.io.ArchRulesFromAdocReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArchRulesFromAdocReaderTest {

    private RelationManager relationManager;
    private ConceptManager conceptManager;

    @BeforeEach
    public void setUp() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    public void givenOnionRuleFile_whenImportingIntoModel_thenModelIsLikeExpected()
            throws IOException, UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    ConceptDoesNotExistException, RecursiveRelationException,
                    InvalidVariableNameException, ConceptAlreadyExistsException,
                    VariableAlreadyExistsException, RelationAlreadyExistsException {

        // given
        final File ruleFile = new File("src/test/resources/architecture-documentation-onion.adoc");

        // when
        ArchRulesFromAdocReader archRulesFromAdocReader = new ArchRulesFromAdocReader();
        RulesAndMappings actualModel =
                archRulesFromAdocReader.readArchitectureRules(
                        ruleFile,
                        relationManager.getRelationByName("is-of-type"),
                        relationManager.getRelationByName("matches"));

        // then
        ConceptManager expectedConceptManager = new ConceptManager();
        RelationManager expectedRelationManager = new RelationManager(expectedConceptManager);
        RulesAndMappings expectedModel =
                TestUtils.prepareModel(expectedConceptManager, expectedRelationManager);

        // Check if architecture rules were correctly imported
        assertEquals(
                expectedModel.getArchitectureRules().size(),
                actualModel.getArchitectureRules().size());
        for (ArchitectureRule rule : actualModel.getArchitectureRules()) {
            assertTrue(expectedModel.getArchitectureRules().contains(rule));
        }

        // Check if mappings were correctly imported
        assertEquals(expectedModel.getMappings().size(), actualModel.getMappings().size());
        for (Mapping mapping : actualModel.getMappings()) {
            assertTrue(expectedModel.getMappings().contains(mapping));
        }
    }
}
