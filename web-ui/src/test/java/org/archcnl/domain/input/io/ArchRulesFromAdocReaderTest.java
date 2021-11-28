package org.archcnl.domain.input.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
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
                expectedModel.getConceptManager().getConcepts().size(),
                rulesConceptsAndRelations.getConceptManager().getConcepts().size());
        for (Concept concept : rulesConceptsAndRelations.getConceptManager().getConcepts()) {
            assertTrue(expectedModel.getConceptManager().getConcepts().contains(concept));
        }

        // Check if relations were correctly imported
        assertEquals(
                expectedModel.getRelationManager().getRelations().size(),
                rulesConceptsAndRelations.getRelationManager().getRelations().size());
        for (Relation relation : rulesConceptsAndRelations.getRelationManager().getRelations()) {
            assertTrue(expectedModel.getRelationManager().getRelations().contains(relation));
        }
    }
}
