package org.archcnl.domain.input.mappings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.archcnl.domain.input.datatypes.RulesConceptsAndRelations;
import org.archcnl.domain.input.datatypes.mappings.AndTriplets;
import org.archcnl.domain.input.datatypes.mappings.ConceptManager;
import org.archcnl.domain.input.datatypes.mappings.ConceptMapping;
import org.archcnl.domain.input.datatypes.mappings.CustomConcept;
import org.archcnl.domain.input.datatypes.mappings.DefaultConcept;
import org.archcnl.domain.input.datatypes.mappings.Triplet;
import org.archcnl.domain.input.datatypes.mappings.Variable;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
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
        assertEquals(12, conceptManager.getConcepts().size());
        assertFalse(conceptManager.doesConceptExist(new DefaultConcept("ABC")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("FamixClass")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("Namespace")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("Enum")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("AnnotationType")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("Method")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("Attribute")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("Inheritance")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("AnnotationInstance")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("AnnotationTypeAttribute")));
        assertTrue(
                conceptManager.doesConceptExist(new DefaultConcept("AnnotationInstanceAttribute")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("Parameter")));
        assertTrue(conceptManager.doesConceptExist(new DefaultConcept("LocalVariable")));
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
                new DefaultConcept("FamixClass"), conceptManager.getConceptByName("FamixClass"));
    }

    @Test
    void givenConceptManager_whenCustomConceptsAdded_thenGetCustomConceptsAsExpected()
            throws ConceptAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    InvalidVariableNameException {
        assertEquals(0, conceptManager.getCustomConcepts().size());
        conceptManager.addConcept(new CustomConcept("Test"));
        conceptManager.addConcept(new DefaultConcept("ABC"));
        assertEquals(1, conceptManager.getCustomConcepts().size());
    }

    @Test
    void givenConceptManager_whenConceptsAreAdded_thenExpectedResults()
            throws ConceptAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, RelationDoesNotExistException,
                    InvalidVariableNameException {
        assertEquals(12, conceptManager.getConcepts().size());
        conceptManager.addConcept(new CustomConcept("Test"));
        conceptManager.addConcept(new DefaultConcept("ABC"));
        assertThrows(
                ConceptAlreadyExistsException.class,
                () -> {
                    conceptManager.addConcept(new DefaultConcept("FamixClass"));
                });
        assertEquals(14, conceptManager.getConcepts().size());
    }

    @Test
    void givenConceptManager_whenAddOrAppendIsCalled_thenExpectedResults()
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, InvalidVariableNameException,
                    ConceptDoesNotExistException, UnrelatedMappingException {
        assertEquals(12, conceptManager.getConcepts().size());
        conceptManager.addOrAppend(new CustomConcept("Test"));
        conceptManager.addOrAppend(new CustomConcept("ABC"));
        conceptManager.addOrAppend(new CustomConcept("ABC"));
        assertEquals(14, conceptManager.getConcepts().size());

        CustomConcept XYZ1 = new CustomConcept("WithMapping");
        List<AndTriplets> when1 = new LinkedList<>();
        List<Triplet> and1 = new LinkedList<>();
        and1.add(
                new Triplet(
                        new Variable("class"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type"),
                        RulesConceptsAndRelations.getInstance()
                                .getConceptManager()
                                .getConceptByName("FamixClass")));
        when1.add(new AndTriplets(and1));
        ConceptMapping mapping1 = new ConceptMapping(new Variable("class"), when1, XYZ1);
        XYZ1.setMapping(mapping1);
        conceptManager.addOrAppend(XYZ1);
        assertEquals(15, conceptManager.getConcepts().size());
        CustomConcept XYZ_extracted =
                (CustomConcept) conceptManager.getConceptByName("WithMapping");
        assertEquals(1, XYZ_extracted.getMapping().getWhenTriplets().size());

        CustomConcept XYZ2 = new CustomConcept("WithMapping");
        List<AndTriplets> when2 = new LinkedList<>();
        List<Triplet> and2 = new LinkedList<>();
        and2.add(
                new Triplet(
                        new Variable("class"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type"),
                        RulesConceptsAndRelations.getInstance()
                                .getConceptManager()
                                .getConceptByName("Enum")));
        when2.add(new AndTriplets(and2));
        ConceptMapping mapping2 = new ConceptMapping(new Variable("class"), when2, XYZ2);
        XYZ2.setMapping(mapping2);
        conceptManager.addOrAppend(XYZ2);
        assertEquals(15, conceptManager.getConcepts().size());
        XYZ_extracted = (CustomConcept) conceptManager.getConceptByName("WithMapping");
        assertEquals(2, XYZ_extracted.getMapping().getWhenTriplets().size());
    }
}
