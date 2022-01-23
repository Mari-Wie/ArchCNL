package org.archcnl.domain.input.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.io.ArchRulesFromAdocReader;
import org.archcnl.domain.common.io.ArchRulesToAdocWriter;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.junit.jupiter.api.Test;

class ArchRuleToAdocWriterTest {

    @Test
    void givenRulesAndMappings_whenWritingAdocFile_thenExpectedResult()
            throws IOException, UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    ConceptDoesNotExistException, InvalidVariableNameException,
                    ConceptAlreadyExistsException, VariableAlreadyExistsException,
                    RelationAlreadyExistsException, UnrelatedMappingException {
        // given
        RulesConceptsAndRelations model = TestUtils.prepareModel();

        // when
        final File file = new File("src/test/resources/WriterTest.adoc");
        ArchRulesToAdocWriter archRulesToAdocWriter = new ArchRulesToAdocWriter();
        archRulesToAdocWriter.writeArchitectureRules(file, model);

        // then
        String actualContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        final File expectedFile = new File("src/test/resources/architecture-documentation.adoc");
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
        assertEquals(
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getConceptDescriptionPattern(), expectedContent),
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getConceptDescriptionPattern(), actualContent));
        assertEquals(
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getRelationDescriptionPattern(), expectedContent),
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getRelationDescriptionPattern(), actualContent));

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
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        ArchRulesFromAdocReader.getConceptDescriptionPattern(),
                        expectedContent,
                        actualContent));
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        ArchRulesFromAdocReader.getRelationDescriptionPattern(),
                        expectedContent,
                        actualContent));
    }
}
