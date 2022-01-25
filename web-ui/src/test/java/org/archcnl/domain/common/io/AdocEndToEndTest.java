package org.archcnl.domain.common.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdocEndToEndTest {

    private RulesConceptsAndRelations rulesConceptsAndRelations;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        rulesConceptsAndRelations = new RulesConceptsAndRelations();
    }

    @Test
    void givenRuleFile_whenImportingAndExportingFile_thenWrittenFileIsAsExpected()
            throws IOException, RelationDoesNotExistException {
        // given
        final File ruleFile = new File("src/test/resources/architecture-documentation.adoc");

        // when
        ArchRulesFromAdocReader archRulesFromAdocReader = new ArchRulesFromAdocReader();
        archRulesFromAdocReader.readArchitectureRules(ruleFile, rulesConceptsAndRelations);

        final File writtenFile = new File("src/test/resources/EndToEndTest.adoc");
        ArchRulesToAdocWriter archRulesToAdocWriter = new ArchRulesToAdocWriter();
        archRulesToAdocWriter.writeArchitectureRules(writtenFile, rulesConceptsAndRelations);

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
