package org.archcnl.ui.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.input.datatypes.RulesAndMappings;
import org.archcnl.domain.input.datatypes.mappings.ConceptManager;
import org.archcnl.domain.input.datatypes.mappings.RelationManager;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.io.ArchRulesFromAdocReader;
import org.archcnl.domain.input.io.ArchRulesToAdocWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdocEndToEndTest {

    private RelationManager relationManager;
    private ConceptManager conceptManager;

    @BeforeEach
    public void setUp() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    public void givenOnionRuleFile_whenImportingAndExportingFile_thenWrittenFileIsAsExpected()
            throws IOException, RelationDoesNotExistException {
        // given
        final File ruleFile = new File("src/test/resources/architecture-documentation-onion.adoc");

        // when
        ArchRulesFromAdocReader archRulesFromAdocReader = new ArchRulesFromAdocReader();
        RulesAndMappings rulesAndMappings =
                archRulesFromAdocReader.readArchitectureRules(
                        ruleFile,
                        relationManager.getRelationByName("is-of-type"),
                        relationManager.getRelationByName("matches"));

        final File writtenFile = new File("src/test/resources/onionDemoEndToEndTest.adoc");
        ArchRulesToAdocWriter archRulesToAdocWriter = new ArchRulesToAdocWriter();
        archRulesToAdocWriter.writeArchitectureRules(writtenFile, rulesAndMappings);

        // then
        String expectedContent = FileUtils.readFileToString(ruleFile, StandardCharsets.UTF_8);
        String actualContent = FileUtils.readFileToString(writtenFile, StandardCharsets.UTF_8);

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
