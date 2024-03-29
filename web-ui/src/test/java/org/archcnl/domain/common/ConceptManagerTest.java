package org.archcnl.domain.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.ConformanceConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.TripletFactory;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;
import org.archcnl.domain.common.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConceptManagerTest {

    private ConceptManager conceptManager;
    private RelationManager relationManager;
    private int inputConceptsCount;
    private int outputConceptsCount;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
        inputConceptsCount = 14;
        outputConceptsCount = 20;
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
        Assertions.assertEquals(inputConceptsCount, conceptManager.getInputConcepts().size());
        Assertions.assertEquals(outputConceptsCount, conceptManager.getOutputConcepts().size());
        Assertions.assertFalse(conceptManager.doesConceptExist("ABC"));

        // Famix
        Assertions.assertTrue(conceptManager.doesConceptExist("FamixClass"));
        Assertions.assertTrue(conceptManager.doesConceptExist("Namespace"));
        Assertions.assertTrue(conceptManager.doesConceptExist("Enum"));
        Assertions.assertTrue(conceptManager.doesConceptExist("AnnotationType"));
        Assertions.assertTrue(conceptManager.doesConceptExist("Method"));
        Assertions.assertTrue(conceptManager.doesConceptExist("Attribute"));
        Assertions.assertTrue(conceptManager.doesConceptExist("Inheritance"));
        Assertions.assertTrue(conceptManager.doesConceptExist("AnnotationInstance"));
        Assertions.assertTrue(conceptManager.doesConceptExist("AnnotationTypeAttribute"));
        Assertions.assertTrue(conceptManager.doesConceptExist("AnnotationInstanceAttribute"));
        Assertions.assertTrue(conceptManager.doesConceptExist("Parameter"));
        Assertions.assertTrue(conceptManager.doesConceptExist("LocalVariable"));
        Assertions.assertTrue(conceptManager.doesConceptExist("PrimitiveType"));

        // Conformance
        Assertions.assertTrue(conceptManager.doesConceptExist("ConformanceCheck"));
        Assertions.assertTrue(conceptManager.doesConceptExist("ArchitectureRule"));
        Assertions.assertTrue(conceptManager.doesConceptExist("ArchitectureViolation"));
        Assertions.assertTrue(conceptManager.doesConceptExist("Proof"));
        Assertions.assertTrue(conceptManager.doesConceptExist("AssertedStatement"));
        Assertions.assertTrue(conceptManager.doesConceptExist("NotInferredStatement"));

        // Main
        Assertions.assertTrue(conceptManager.doesConceptExist("SoftwareArtifactFile"));
    }

    @Test
    void givenFreshConceptManager_whenGetConceptByName_thenExpectedResults()
            throws ConceptDoesNotExistException {
        Assertions.assertEquals(Optional.empty(), conceptManager.getConceptByName("ABC"));
        Assertions.assertEquals(
                Optional.of(new FamixConcept("FamixClass", "")),
                conceptManager.getConceptByName("FamixClass"));
    }

    @Test
    void givenConceptManager_whenCustomConceptsAdded_thenGetCustomConceptsAsExpected()
            throws ConceptAlreadyExistsException, UnsupportedObjectTypeException {
        Assertions.assertEquals(0, conceptManager.getCustomConcepts().size());
        conceptManager.addConcept(new CustomConcept("Test", ""));
        conceptManager.addConcept(new FamixConcept("ABC", ""));
        Assertions.assertEquals(1, conceptManager.getCustomConcepts().size());
    }

    @Test
    void givenConceptManager_whenConceptsAreAdded_thenExpectedResults()
            throws ConceptAlreadyExistsException, UnsupportedObjectTypeException {
        Assertions.assertEquals(inputConceptsCount, conceptManager.getInputConcepts().size());
        Assertions.assertEquals(outputConceptsCount, conceptManager.getOutputConcepts().size());
        conceptManager.addConcept(new CustomConcept("Test", ""));
        conceptManager.addConcept(new FamixConcept("ABC", ""));
        conceptManager.addConcept(new ConformanceConcept("ConformanceConcept", ""));
        Assertions.assertThrows(
                ConceptAlreadyExistsException.class,
                () -> {
                    conceptManager.addConcept(new FamixConcept("FamixClass", ""));
                });
        Assertions.assertEquals(inputConceptsCount + 2, conceptManager.getInputConcepts().size());
        Assertions.assertEquals(outputConceptsCount + 3, conceptManager.getOutputConcepts().size());
    }

    @Test
    void givenConceptManager_whenAddOrAppendIsCalled_thenExpectedResults()
            throws UnsupportedObjectTypeException, ConceptDoesNotExistException,
                    UnrelatedMappingException {
        Assertions.assertEquals(inputConceptsCount, conceptManager.getInputConcepts().size());
        Assertions.assertEquals(outputConceptsCount, conceptManager.getOutputConcepts().size());
        conceptManager.addOrAppend(new CustomConcept("Test", ""));
        conceptManager.addOrAppend(new CustomConcept("ABC", ""));
        conceptManager.addOrAppend(new CustomConcept("ABC", ""));
        Assertions.assertEquals(inputConceptsCount + 2, conceptManager.getInputConcepts().size());
        Assertions.assertEquals(outputConceptsCount + 2, conceptManager.getOutputConcepts().size());

        final String conceptName = "ConceptName";

        final CustomConcept concept1 = new CustomConcept(conceptName, "");
        final List<AndTriplets> when1 = new LinkedList<>();
        final List<Triplet> and1 = new LinkedList<>();
        and1.add(
                TripletFactory.createTriplet(
                        new Variable("class"),
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("FamixClass").get()));
        when1.add(new AndTriplets(and1));
        final ConceptMapping mapping1 = new ConceptMapping(new Variable("class"), when1, concept1);
        concept1.setMapping(mapping1);
        conceptManager.addOrAppend(concept1);
        Assertions.assertEquals(inputConceptsCount + 3, conceptManager.getInputConcepts().size());
        Assertions.assertEquals(outputConceptsCount + 3, conceptManager.getOutputConcepts().size());
        CustomConcept extractedConcept_extracted =
                (CustomConcept) conceptManager.getConceptByName(conceptName).get();
        Assertions.assertEquals(
                1, extractedConcept_extracted.getMapping().get().getWhenTriplets().size());

        final CustomConcept concept2 = new CustomConcept(conceptName, "");
        final List<AndTriplets> when2 = new LinkedList<>();
        final List<Triplet> and2 = new LinkedList<>();
        and2.add(
                TripletFactory.createTriplet(
                        new Variable("class"),
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("Enum").get()));
        when2.add(new AndTriplets(and2));
        final ConceptMapping mapping2 = new ConceptMapping(new Variable("class"), when2, concept2);
        concept2.setMapping(mapping2);
        conceptManager.addOrAppend(concept2);
        Assertions.assertEquals(inputConceptsCount + 3, conceptManager.getInputConcepts().size());
        Assertions.assertEquals(outputConceptsCount + 3, conceptManager.getOutputConcepts().size());
        extractedConcept_extracted =
                (CustomConcept) conceptManager.getConceptByName(conceptName).get();
        Assertions.assertEquals(
                2, extractedConcept_extracted.getMapping().get().getWhenTriplets().size());
    }
}
