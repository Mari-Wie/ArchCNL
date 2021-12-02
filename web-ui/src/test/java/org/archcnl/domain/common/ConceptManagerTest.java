package org.archcnl.domain.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConceptManagerTest {

    private ConceptManager conceptManager;
    private int inputConceptsCount;
    private int outputConceptsCount;

    @BeforeEach
    private void setup() {
        conceptManager = new ConceptManager();
        inputConceptsCount = 12;
        outputConceptsCount = 18;
    }

    @Test
    void givenConceptManager_whenGetInputConcepts_thenExpectedResults() {
        // given and when
        final List<Concept> concepts = conceptManager.getInputConcepts();

        // then
        Assertions.assertEquals(inputConceptsCount, concepts.size());
        Assertions.assertFalse(concepts.stream().anyMatch(ConformanceConcept.class::isInstance));
    }

    @Test
    void givenConceptManager_whenGetOutputConcepts_thenExpectedResults() {
        // given and when
        final List<Concept> concepts = conceptManager.getOutputConcepts();

        // then
        Assertions.assertEquals(outputConceptsCount, concepts.size());
    }

    @Test
    void givenConceptManager_whenCreated_thenExpectedConcepts()
            throws ConceptDoesNotExistException {
        assertEquals(inputConceptsCount, conceptManager.getInputConcepts().size());
        assertEquals(outputConceptsCount, conceptManager.getOutputConcepts().size());
        assertFalse(conceptManager.doesConceptExist(new FamixConcept("ABC", "")));

        // Famix
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("FamixClass", "")));
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("Namespace", "")));
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("Enum", "")));
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("AnnotationType", "")));
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("Method", "")));
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("Attribute", "")));
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("Inheritance", "")));
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("AnnotationInstance", "")));
        assertTrue(
                conceptManager.doesConceptExist(new FamixConcept("AnnotationTypeAttribute", "")));
        assertTrue(
                conceptManager.doesConceptExist(
                        new FamixConcept("AnnotationInstanceAttribute", "")));
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("Parameter", "")));
        assertTrue(conceptManager.doesConceptExist(new FamixConcept("LocalVariable", "")));

        // Conformance
        assertTrue(conceptManager.doesConceptExist(new ConformanceConcept("ConformanceCheck", "")));
        assertTrue(conceptManager.doesConceptExist(new ConformanceConcept("ArchitectureRule", "")));
        assertTrue(
                conceptManager.doesConceptExist(
                        new ConformanceConcept("ArchitectureViolation", "")));
        assertTrue(conceptManager.doesConceptExist(new ConformanceConcept("Proof", "")));
        assertTrue(
                conceptManager.doesConceptExist(new ConformanceConcept("AssertedStatement", "")));
        assertTrue(
                conceptManager.doesConceptExist(
                        new ConformanceConcept("NotInferredStatement", "")));
    }

    @Test
    void givenFreshConceptManager_whenGetConceptByName_thenExpectedResults()
            throws ConceptDoesNotExistException {
        assertThrows(
                ConceptDoesNotExistException.class,
                () -> {
                    conceptManager.getConceptByName("ABC");
                });
        assertEquals(
                new FamixConcept("FamixClass", ""), conceptManager.getConceptByName("FamixClass"));
    }

    @Test
    void givenConceptManager_whenCustomConceptsAdded_thenGetCustomConceptsAsExpected()
            throws ConceptAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    InvalidVariableNameException {
        assertEquals(0, conceptManager.getCustomConcepts().size());
        conceptManager.addConcept(new CustomConcept("Test", ""));
        conceptManager.addConcept(new FamixConcept("ABC", ""));
        assertEquals(1, conceptManager.getCustomConcepts().size());
    }

    @Test
    void givenConceptManager_whenConceptsAreAdded_thenExpectedResults()
            throws ConceptAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    InvalidVariableNameException {
        assertEquals(inputConceptsCount, conceptManager.getInputConcepts().size());
        assertEquals(outputConceptsCount, conceptManager.getOutputConcepts().size());
        conceptManager.addConcept(new CustomConcept("Test", ""));
        conceptManager.addConcept(new FamixConcept("ABC", ""));
        conceptManager.addConcept(new ConformanceConcept("ConformanceConcept", ""));
        assertThrows(
                ConceptAlreadyExistsException.class,
                () -> {
                    conceptManager.addConcept(new FamixConcept("FamixClass", ""));
                });
        assertEquals(inputConceptsCount + 2, conceptManager.getInputConcepts().size());
        assertEquals(outputConceptsCount + 3, conceptManager.getOutputConcepts().size());
    }

    @Test
    void givenConceptManager_whenAddOrAppendIsCalled_thenExpectedResults()
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, InvalidVariableNameException,
                    ConceptDoesNotExistException, UnrelatedMappingException {
        assertEquals(inputConceptsCount, conceptManager.getInputConcepts().size());
        assertEquals(outputConceptsCount, conceptManager.getOutputConcepts().size());
        conceptManager.addOrAppend(new CustomConcept("Test", ""));
        conceptManager.addOrAppend(new CustomConcept("ABC", ""));
        conceptManager.addOrAppend(new CustomConcept("ABC", ""));
        assertEquals(inputConceptsCount + 2, conceptManager.getInputConcepts().size());
        assertEquals(outputConceptsCount + 2, conceptManager.getOutputConcepts().size());

        String conceptName = "ConceptName";

        CustomConcept concept1 = new CustomConcept(conceptName, "");
        List<AndTriplets> when1 = new LinkedList<>();
        List<Triplet> and1 = new LinkedList<>();
        and1.add(
                TripletFactory.createTriplet(
                        new Variable("class"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type"),
                        RulesConceptsAndRelations.getInstance()
                                .getConceptManager()
                                .getConceptByName("FamixClass")));
        when1.add(new AndTriplets(and1));
        ConceptMapping mapping1 = new ConceptMapping(new Variable("class"), when1, concept1);
        concept1.setMapping(mapping1);
        conceptManager.addOrAppend(concept1);
        assertEquals(inputConceptsCount + 3, conceptManager.getInputConcepts().size());
        assertEquals(outputConceptsCount + 3, conceptManager.getOutputConcepts().size());
        CustomConcept extractedConcept_extracted =
                (CustomConcept) conceptManager.getConceptByName(conceptName);
        assertEquals(1, extractedConcept_extracted.getMapping().get().getWhenTriplets().size());

        CustomConcept concept2 = new CustomConcept(conceptName, "");
        List<AndTriplets> when2 = new LinkedList<>();
        List<Triplet> and2 = new LinkedList<>();
        and2.add(
                TripletFactory.createTriplet(
                        new Variable("class"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type"),
                        RulesConceptsAndRelations.getInstance()
                                .getConceptManager()
                                .getConceptByName("Enum")));
        when2.add(new AndTriplets(and2));
        ConceptMapping mapping2 = new ConceptMapping(new Variable("class"), when2, concept2);
        concept2.setMapping(mapping2);
        conceptManager.addOrAppend(concept2);
        assertEquals(inputConceptsCount + 3, conceptManager.getInputConcepts().size());
        assertEquals(outputConceptsCount + 3, conceptManager.getOutputConcepts().size());
        extractedConcept_extracted = (CustomConcept) conceptManager.getConceptByName(conceptName);
        assertEquals(2, extractedConcept_extracted.getMapping().get().getWhenTriplets().size());
    }
}
