package org.archcnl.domain.input.mappings;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.FamixConcept;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.Variable;
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

    @BeforeEach
    private void setup() {
        conceptManager = new ConceptManager();
    }

    @Test
    void givenConceptManager_whenCreated_thenExpectedConcepts()
            throws ConceptDoesNotExistException {
        Assertions.assertEquals(12, conceptManager.getConcepts().size());
        Assertions.assertFalse(conceptManager.doesConceptExist(new FamixConcept("ABC")));
        Assertions.assertTrue(conceptManager.doesConceptExist(new FamixConcept("FamixClass")));
        Assertions.assertTrue(conceptManager.doesConceptExist(new FamixConcept("Namespace")));
        Assertions.assertTrue(conceptManager.doesConceptExist(new FamixConcept("Enum")));
        Assertions.assertTrue(conceptManager.doesConceptExist(new FamixConcept("AnnotationType")));
        Assertions.assertTrue(conceptManager.doesConceptExist(new FamixConcept("Method")));
        Assertions.assertTrue(conceptManager.doesConceptExist(new FamixConcept("Attribute")));
        Assertions.assertTrue(conceptManager.doesConceptExist(new FamixConcept("Inheritance")));
        Assertions.assertTrue(
                conceptManager.doesConceptExist(new FamixConcept("AnnotationInstance")));
        Assertions.assertTrue(
                conceptManager.doesConceptExist(new FamixConcept("AnnotationTypeAttribute")));
        Assertions.assertTrue(
                conceptManager.doesConceptExist(new FamixConcept("AnnotationInstanceAttribute")));
        Assertions.assertTrue(conceptManager.doesConceptExist(new FamixConcept("Parameter")));
        Assertions.assertTrue(conceptManager.doesConceptExist(new FamixConcept("LocalVariable")));
    }

    @Test
    void givenFreshConceptManager_whenGetConceptByName_thenExpectedResults()
            throws ConceptDoesNotExistException {
        Assertions.assertThrows(
                ConceptDoesNotExistException.class,
                () -> {
                    conceptManager.getConceptByName("ABC");
                });
        Assertions.assertEquals(
                new FamixConcept("FamixClass"), conceptManager.getConceptByName("FamixClass"));
    }

    @Test
    void givenConceptManager_whenCustomConceptsAdded_thenGetCustomConceptsAsExpected()
            throws ConceptAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    InvalidVariableNameException {
        Assertions.assertEquals(0, conceptManager.getCustomConcepts().size());
        conceptManager.addConcept(new CustomConcept("Test"));
        conceptManager.addConcept(new FamixConcept("ABC"));
        Assertions.assertEquals(1, conceptManager.getCustomConcepts().size());
    }

    @Test
    void givenConceptManager_whenConceptsAreAdded_thenExpectedResults()
            throws ConceptAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    InvalidVariableNameException {
        Assertions.assertEquals(12, conceptManager.getConcepts().size());
        conceptManager.addConcept(new CustomConcept("Test"));
        conceptManager.addConcept(new FamixConcept("ABC"));
        Assertions.assertThrows(
                ConceptAlreadyExistsException.class,
                () -> {
                    conceptManager.addConcept(new FamixConcept("FamixClass"));
                });
        Assertions.assertEquals(14, conceptManager.getConcepts().size());
    }

    @Test
    void givenConceptManager_whenAddOrAppendIsCalled_thenExpectedResults()
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, InvalidVariableNameException,
                    ConceptDoesNotExistException, UnrelatedMappingException {
        Assertions.assertEquals(12, conceptManager.getConcepts().size());
        conceptManager.addOrAppend(new CustomConcept("Test"));
        conceptManager.addOrAppend(new CustomConcept("ABC"));
        conceptManager.addOrAppend(new CustomConcept("ABC"));
        Assertions.assertEquals(14, conceptManager.getConcepts().size());

        final String conceptName = "ConceptName";

        final CustomConcept concept1 = new CustomConcept(conceptName);
        final List<AndTriplets> when1 = new LinkedList<>();
        final List<Triplet> and1 = new LinkedList<>();
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
        final ConceptMapping mapping1 = new ConceptMapping(new Variable("class"), when1, concept1);
        concept1.setMapping(mapping1);
        conceptManager.addOrAppend(concept1);
        Assertions.assertEquals(15, conceptManager.getConcepts().size());
        CustomConcept extractedConcept_extracted =
                (CustomConcept) conceptManager.getConceptByName(conceptName);
        Assertions.assertEquals(
                1, extractedConcept_extracted.getMapping().get().getWhenTriplets().size());

        final CustomConcept concept2 = new CustomConcept(conceptName);
        final List<AndTriplets> when2 = new LinkedList<>();
        final List<Triplet> and2 = new LinkedList<>();
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
        final ConceptMapping mapping2 = new ConceptMapping(new Variable("class"), when2, concept2);
        concept2.setMapping(mapping2);
        conceptManager.addOrAppend(concept2);
        Assertions.assertEquals(15, conceptManager.getConcepts().size());
        extractedConcept_extracted = (CustomConcept) conceptManager.getConceptByName(conceptName);
        Assertions.assertEquals(
                2, extractedConcept_extracted.getMapping().get().getWhenTriplets().size());
    }
}
