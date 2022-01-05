package org.archcnl.domain.input.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.Concept;
import org.archcnl.domain.common.Relation;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArchRulesFromAdocReaderTest {

    private RulesConceptsAndRelations rulesConceptsAndRelations;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        rulesConceptsAndRelations = new RulesConceptsAndRelations();
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
        ArchRulesFromAdocReader archRulesFromAdocReader = new ArchRulesFromAdocReader();
        archRulesFromAdocReader.readArchitectureRules(ruleFile, rulesConceptsAndRelations);

        // then
        RulesConceptsAndRelations expectedModel = TestUtils.prepareModel();

        // Check if architecture rules were correctly imported
        assertEquals(
                expectedModel.getArchitectureRuleManager().getArchitectureRules().size(),
                rulesConceptsAndRelations
                        .getArchitectureRuleManager()
                        .getArchitectureRules()
                        .size());
        for (ArchitectureRule rule :
                rulesConceptsAndRelations.getArchitectureRuleManager().getArchitectureRules()) {
            assertTrue(
                    expectedModel
                            .getArchitectureRuleManager()
                            .getArchitectureRules()
                            .contains(rule));
        }

        // Check if concepts were correctly imported
        assertEquals(
                expectedModel.getConceptManager().getInputConcepts().size(),
                rulesConceptsAndRelations.getConceptManager().getInputConcepts().size());
        for (Concept concept : rulesConceptsAndRelations.getConceptManager().getInputConcepts()) {
            assertTrue(expectedModel.getConceptManager().getInputConcepts().contains(concept));
        }

        // Check if relations were correctly imported
        assertEquals(
                expectedModel.getRelationManager().getInputRelations().size(),
                rulesConceptsAndRelations.getRelationManager().getInputRelations().size());
        for (Relation relation :
                rulesConceptsAndRelations.getRelationManager().getInputRelations()) {
            assertTrue(expectedModel.getRelationManager().getInputRelations().contains(relation));
        }
    }

    @Test
    void givenRulesAndMappingsInAdocFormat_whenMatchingPatterns_thenMatchesAreFound()
            throws IOException {
        // given, when
        final File ruleFile = new File("src/test/resources/ReaderParsingTest.adoc");
        String rulesFileString = FileUtils.readFileToString(ruleFile, StandardCharsets.UTF_8);
        // then
        assertEquals(
                2,
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getRulePattern(), rulesFileString));
        assertEquals(
                1,
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getConceptDescriptionPattern(), rulesFileString));
        assertEquals(
                1,
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getRelationDescriptionPattern(), rulesFileString));
        assertEquals(
                5,
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getRelationMappingPattern(), rulesFileString));
        assertEquals(
                3,
                TestUtils.numberOfMatches(
                        ArchRulesFromAdocReader.getConceptMappingPattern(), rulesFileString));
    }
}
