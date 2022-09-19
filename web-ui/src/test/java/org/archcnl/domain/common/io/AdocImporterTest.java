package org.archcnl.domain.common.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;
import org.archcnl.domain.common.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.common.io.importhelper.DescriptionParser;
import org.archcnl.domain.common.io.importhelper.MappingParser;
import org.archcnl.domain.common.io.importhelper.RuleParser;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdocImporterTest {

    private ArchitectureRuleManager ruleManager;
    private ConceptManager conceptManager;
    private RelationManager relationManager;
    private Queue<FreeTextQuery> freeTextQueryQueue;
    private Queue<Query> customQueryQueue;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        ruleManager = new ArchitectureRuleManager();
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
        freeTextQueryQueue = new LinkedList<>();
        customQueryQueue = new LinkedList<>();
    }

    @Test
    void givenRuleFile_whenImportingIntoModel_thenModelIsLikeExpected()
            throws IOException, UnsupportedObjectTypeException, ConceptDoesNotExistException,
                    ConceptAlreadyExistsException, RelationAlreadyExistsException,
                    UnrelatedMappingException {

        // given
        final File ruleFile = new File("src/test/resources/architecture-documentation.adoc");

        // when
        AdocImporter adocImporter =
                new AdocImporter(
                        ruleManager,
                        conceptManager,
                        relationManager,
                        freeTextQueryQueue,
                        customQueryQueue);
        adocImporter.readFromAdoc(ruleFile);

        // then
        ArchitectureRuleManager expectedRuleManager = TestUtils.prepareRuleManager();
        ConceptManager expectedConceptManager = TestUtils.prepareConceptManager();
        RelationManager expectedRelationManager = TestUtils.prepareRelationManager();
        List<Query> expectedCustomQueries = TestUtils.prepareCustomQueries();
        List<FreeTextQuery> expectedFreeTextQueries = TestUtils.prepareFreeTextQueries();

        // Check if architecture rules were correctly imported
        Assertions.assertEquals(
                expectedRuleManager.getArchitectureRules().size(),
                ruleManager.getArchitectureRules().size());
        for (ArchitectureRule rule : ruleManager.getArchitectureRules()) {
            Assertions.assertTrue(expectedRuleManager.getArchitectureRules().contains(rule));
        }

        // Check if concepts were correctly imported
        Assertions.assertEquals(
                expectedConceptManager.getInputConcepts().size(),
                conceptManager.getInputConcepts().size());
        for (Concept concept : conceptManager.getInputConcepts()) {
            Assertions.assertTrue(expectedConceptManager.getInputConcepts().contains(concept));
            Assertions.assertEquals(
                    expectedConceptManager
                            .getConceptByName(concept.getName())
                            .get()
                            .getDescription(),
                    concept.getDescription());
        }

        // Check if relations were correctly imported
        Assertions.assertEquals(
                expectedRelationManager.getInputRelations().size(),
                relationManager.getInputRelations().size());
        for (Relation relation : relationManager.getInputRelations()) {
            Assertions.assertTrue(expectedRelationManager.getInputRelations().contains(relation));
            Assertions.assertEquals(
                    expectedRelationManager
                            .getRelationByName(relation.getName())
                            .get()
                            .getDescription(),
                    relation.getDescription());
        }

        Assertions.assertEquals(expectedFreeTextQueries.size(), freeTextQueryQueue.size());
        while (!freeTextQueryQueue.isEmpty()) {
            FreeTextQuery query = freeTextQueryQueue.poll();
            Assertions.assertTrue(
                    expectedFreeTextQueries.stream()
                            .anyMatch(q -> q.getName().equals(query.getName())));
            Assertions.assertTrue(
                    expectedFreeTextQueries.stream()
                            .anyMatch(q -> q.getQueryString().equals(query.getQueryString())));
        }

        Assertions.assertEquals(expectedCustomQueries.size(), customQueryQueue.size());
        while (!customQueryQueue.isEmpty()) {
            Query query = customQueryQueue.poll();
            Assertions.assertTrue(
                    expectedCustomQueries.stream()
                            .anyMatch(q -> q.getName().equals(query.getName())));
            Assertions.assertTrue(
                    expectedCustomQueries.stream()
                            .anyMatch(q -> q.transformToAdoc().equals(query.transformToAdoc())));
        }
    }

    @Test
    void givenRulesAndMappingsInAdocFormat_whenMatchingPatterns_thenMatchesAreFound()
            throws IOException {
        // given, when
        final File ruleFile = new File("src/test/resources/ReaderParsingTest.adoc");
        String rulesFileString = FileUtils.readFileToString(ruleFile, StandardCharsets.UTF_8);
        // then
        Assertions.assertEquals(
                2, TestUtils.numberOfMatches(RuleParser.getRuleContentPattern(), rulesFileString));
        Assertions.assertEquals(
                1,
                TestUtils.numberOfMatches(
                        DescriptionParser.getConceptDescriptionPattern(), rulesFileString));
        Assertions.assertEquals(
                1,
                TestUtils.numberOfMatches(
                        DescriptionParser.getRelationDescriptionPattern(), rulesFileString));
        Assertions.assertEquals(
                5,
                TestUtils.numberOfMatches(
                        MappingParser.getRelationMappingPattern(), rulesFileString));
        Assertions.assertEquals(
                3,
                TestUtils.numberOfMatches(
                        MappingParser.getConceptMappingPattern(), rulesFileString));
    }
}
