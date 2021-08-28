package org.archcnl.ui.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.archcnl.input.datatypes.RulesAndMappings;
import org.archcnl.input.datatypes.mappings.ConceptManager;
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
import org.archcnl.input.io.ArchRulesToAdocWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArchRuleToAdocWriterTest {

    private RelationManager relationManager;
    private ConceptManager conceptManager;

    @BeforeEach
    public void setUp() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    public void givenRulesAndMappings_whenWritingAdocFile_thenExpectedResult()
            throws IOException, UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    ConceptDoesNotExistException, RecursiveRelationException,
                    InvalidVariableNameException, ConceptAlreadyExistsException,
                    VariableAlreadyExistsException, RelationAlreadyExistsException {
        // given
        RulesAndMappings rulesAndMappings = TestUtils.prepareModel(conceptManager, relationManager);

        // when
        final File file = new File("src/test/resources/onionWriterTest.adoc");
        ArchRulesToAdocWriter archRulesToAdocWriter = new ArchRulesToAdocWriter();
        archRulesToAdocWriter.writeArchitectureRules(file, rulesAndMappings);

        // then
        String actualContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        final File expectedFile =
                new File("src/test/resources/architecture-documentation-onion.adoc");
        String expectedContent = FileUtils.readFileToString(expectedFile, StandardCharsets.UTF_8);

        assertEquals(
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getRulePattern(), expectedContent),
                TestUtils.numberOfMatches(ArchRulesFromAdocReader.getRulePattern(), actualContent));
        assertEquals(
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getConceptMappingPattern(), expectedContent),
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getConceptMappingPattern(), actualContent));
        assertEquals(
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getRelationMappingPattern(), expectedContent),
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getRelationMappingPattern(), actualContent));

        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        ArchRulesFromAdocReader.getRulePattern(), expectedContent, actualContent));
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        ArchRulesFromAdocReader.getConceptMappingPattern(),
                        expectedContent,
                        actualContent));
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        ArchRulesFromAdocReader.getRelationMappingPattern(),
                        expectedContent,
                        actualContent));
    }
}
