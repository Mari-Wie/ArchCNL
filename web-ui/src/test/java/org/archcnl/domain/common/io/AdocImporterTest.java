package org.archcnl.domain.common.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.io.importhelper.MappingDescriptionExtractor;
import org.archcnl.domain.common.io.importhelper.MappingExtractor;
import org.archcnl.domain.common.io.importhelper.RuleExtractor;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
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
            throws IOException, UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    ConceptDoesNotExistException, InvalidVariableNameException,
                    ConceptAlreadyExistsException, VariableAlreadyExistsException,
                    RelationAlreadyExistsException, UnrelatedMappingException {

        // given
        final File ruleFile = new File("src/test/resources/architecture-documentation.adoc");

        // when
        AdocImporter adocImporter = new AdocImporter();
        adocImporter.readFromAdoc(
                ruleFile,
                ruleManager,
                conceptManager,
                relationManager,
                freeTextQueryQueue,
                customQueryQueue);

        // then
        RulesConceptsAndRelations expectedModel = TestUtils.prepareModel();

        // Check if architecture rules were correctly imported
        Assertions.assertEquals(
                expectedModel.getArchitectureRuleManager().getArchitectureRules().size(),
                ruleManager.getArchitectureRules().size());
        for (ArchitectureRule rule : ruleManager.getArchitectureRules()) {
            Assertions.assertTrue(
                    expectedModel
                            .getArchitectureRuleManager()
                            .getArchitectureRules()
                            .contains(rule));
        }

        // Check if concepts were correctly imported
        Assertions.assertEquals(
                expectedModel.getConceptManager().getInputConcepts().size(),
                conceptManager.getInputConcepts().size());
        for (Concept concept : conceptManager.getInputConcepts()) {
            Assertions.assertTrue(
                    expectedModel.getConceptManager().getInputConcepts().contains(concept));
            Assertions.assertEquals(
                    expectedModel
                            .getConceptManager()
                            .getConceptByName(concept.getName())
                            .get()
                            .getDescription(),
                    concept.getDescription());
        }

        // Check if relations were correctly imported
        Assertions.assertEquals(
                expectedModel.getRelationManager().getInputRelations().size(),
                relationManager.getInputRelations().size());
        for (Relation relation : relationManager.getInputRelations()) {
            Assertions.assertTrue(
                    expectedModel.getRelationManager().getInputRelations().contains(relation));
            Assertions.assertEquals(
                    expectedModel
                            .getRelationManager()
                            .getRelationByName(relation.getName())
                            .get()
                            .getDescription(),
                    relation.getDescription());
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
                2,
                TestUtils.numberOfMatches(RuleExtractor.getRuleContentPattern(), rulesFileString));
        Assertions.assertEquals(
                1,
                TestUtils.numberOfMatches(
                        MappingDescriptionExtractor.getConceptDescriptionPattern(),
                        rulesFileString));
        Assertions.assertEquals(
                1,
                TestUtils.numberOfMatches(
                        MappingDescriptionExtractor.getRelationDescriptionPattern(),
                        rulesFileString));
        Assertions.assertEquals(
                5,
                TestUtils.numberOfMatches(
                        MappingExtractor.getRelationMappingPattern(), rulesFileString));
        Assertions.assertEquals(
                3,
                TestUtils.numberOfMatches(
                        MappingExtractor.getConceptMappingPattern(), rulesFileString));
    }
}
