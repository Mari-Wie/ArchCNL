package org.archcnl.domain.common.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.io.importhelper.DescriptionParser;
import org.archcnl.domain.common.io.importhelper.MappingParser;
import org.archcnl.domain.common.io.importhelper.RuleParser;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;
import org.junit.jupiter.api.Test;

class AdocExporterTest {

    @Test
    void givenRulesAndMappings_whenWritingAdocFile_thenExpectedResult()
            throws IOException, UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    ConceptDoesNotExistException, InvalidVariableNameException,
                    ConceptAlreadyExistsException, VariableAlreadyExistsException,
                    RelationAlreadyExistsException, UnrelatedMappingException {
        // given
        ArchitectureRuleManager ruleManager = TestUtils.prepareRuleManager();
        ConceptManager conceptManager = TestUtils.prepareConceptManager();
        RelationManager relationManager = TestUtils.prepareRelationManager();
        List<FreeTextQuery> freeTextQueries = new LinkedList<>();
        List<Query> customQueries = new LinkedList<>();

        // when
        final File file = new File("src/test/resources/WriterTest.adoc");
        AdocExporter adocExporter = new AdocExporter();
        adocExporter.writeToAdoc(
                file, ruleManager, conceptManager, relationManager, customQueries, freeTextQueries);

        // then
        String actualContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        final File expectedFile = new File("src/test/resources/architecture-documentation.adoc");
        String expectedContent = FileUtils.readFileToString(expectedFile, StandardCharsets.UTF_8);

        assertEquals(
                TestUtils.numberOfMatches(RuleParser.getRuleContentPattern(), expectedContent),
                TestUtils.numberOfMatches(RuleParser.getRuleContentPattern(), actualContent));
        assertEquals(
                TestUtils.numberOfMatches(
                        MappingParser.getConceptMappingPattern(), expectedContent),
                TestUtils.numberOfMatches(MappingParser.getConceptMappingPattern(), actualContent));
        assertEquals(
                TestUtils.numberOfMatches(
                        MappingParser.getRelationMappingPattern(), expectedContent),
                TestUtils.numberOfMatches(
                        MappingParser.getRelationMappingPattern(), actualContent));
        assertEquals(
                TestUtils.numberOfMatches(
                        DescriptionParser.getConceptDescriptionPattern(), expectedContent),
                TestUtils.numberOfMatches(
                        DescriptionParser.getConceptDescriptionPattern(), actualContent));
        assertEquals(
                TestUtils.numberOfMatches(
                        DescriptionParser.getRelationDescriptionPattern(), expectedContent),
                TestUtils.numberOfMatches(
                        DescriptionParser.getRelationDescriptionPattern(), actualContent));

        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        RuleParser.getRuleContentPattern(), expectedContent, actualContent));
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        MappingParser.getConceptMappingPattern(), expectedContent, actualContent));
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        MappingParser.getRelationMappingPattern(), expectedContent, actualContent));
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        DescriptionParser.getConceptDescriptionPattern(),
                        expectedContent,
                        actualContent));
        assertTrue(
                TestUtils.doAllMatchesExistInSecondString(
                        DescriptionParser.getRelationDescriptionPattern(),
                        expectedContent,
                        actualContent));
    }
}
