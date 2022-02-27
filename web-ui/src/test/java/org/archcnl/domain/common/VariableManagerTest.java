package org.archcnl.domain.common;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;
import org.archcnl.domain.common.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VariableManagerTest {

    private VariableManager variableManager;

    @BeforeEach
    void setup() {
        variableManager = new VariableManager();
    }

    @Test
    void givenMapping_whenCheckingDynamicTypesOfConceptMappings_thenNoConflicts()
            throws UnrelatedMappingException, UnsupportedObjectTypeException,
                    ConceptDoesNotExistException, ConceptAlreadyExistsException {
        // given
        ConceptManager conceptManager = TestUtils.prepareConceptManager();
        List<CustomConcept> customConcepts = conceptManager.getCustomConcepts();

        // when and then
        for (CustomConcept concept : customConcepts) {
            Optional<ConceptMapping> mapping = concept.getMapping();
            if (mapping.isPresent()) {
                for (AndTriplets andTriplets : mapping.get().getWhenTriplets()) {
                    Assertions.assertFalse(variableManager.hasConflictingDynamicTypes(andTriplets));
                }
            }
        }
    }

    @Test
    void givenMapping_whenCheckingDynamicTypesOfRelationMappings_thenNoConflicts()
            throws UnsupportedObjectTypeException, ConceptDoesNotExistException,
                    ConceptAlreadyExistsException, UnrelatedMappingException,
                    RelationAlreadyExistsException {
        // given
        RelationManager relationManager = TestUtils.prepareRelationManager();
        List<CustomRelation> customRelations = relationManager.getCustomRelations();

        // when and then
        for (CustomRelation relation : customRelations) {
            Optional<RelationMapping> mapping = relation.getMapping();
            if (mapping.isPresent()) {
                for (AndTriplets andTriplets : mapping.get().getWhenTriplets()) {
                    Assertions.assertFalse(variableManager.hasConflictingDynamicTypes(andTriplets));
                }
            }
        }
    }

    @Test
    void givenConflictingAndTriplets1_whenCheckingDynamicTypes_thenConflictDetected()
            throws ConceptDoesNotExistException {
        // given
        ConceptManager conceptManager = new ConceptManager();
        RelationManager relationManager = new RelationManager(conceptManager);

        Variable classVariable = new Variable("class");
        Triplet triplet1 =
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("FamixClass").get());
        Triplet triplet2 =
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("matches").get(),
                        new StringValue("random string"));
        AndTriplets andTriplets = new AndTriplets(Arrays.asList(triplet1, triplet2));

        // when and then
        // this is a conflict as ?class is of type FamixClass after the first triplet
        // while the matches relation is only defined for string variables as subjects
        Assertions.assertTrue(variableManager.hasConflictingDynamicTypes(andTriplets));
        Assertions.assertTrue(
                variableManager
                        .getVariableByName(classVariable.getName())
                        .get()
                        .hasConflictingDynamicTypes());
    }

    @Test
    void givenConflictingAndTriplets2_whenCheckingDynamicTypes_thenConflictDetected()
            throws ConceptDoesNotExistException {
        // given
        ConceptManager conceptManager = new ConceptManager();
        RelationManager relationManager = new RelationManager(conceptManager);

        Variable classVariable = new Variable("class");
        Variable name = new Variable("name");
        Triplet triplet1 =
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("FamixClass").get());
        Triplet triplet2 =
                new Triplet(
                        classVariable, relationManager.getRelationByName("hasName").get(), name);
        Triplet triplet3 =
                new Triplet(
                        name, relationManager.getRelationByName("matches").get(), classVariable);
        AndTriplets andTriplets = new AndTriplets(Arrays.asList(triplet1, triplet2, triplet3));

        // when and then
        // The matches relation is only defined for strings and string variables as object.
        // But in triplet3 the object variable has type FamixClass
        Assertions.assertTrue(variableManager.hasConflictingDynamicTypes(andTriplets));
        Assertions.assertFalse(
                variableManager
                        .getVariableByName(name.getName())
                        .get()
                        .hasConflictingDynamicTypes());
        Assertions.assertTrue(
                variableManager
                        .getVariableByName(classVariable.getName())
                        .get()
                        .hasConflictingDynamicTypes());
    }

    @Test
    /**
     * This test shows the limitations of the current dynamicTypeChecker. It reports a type conflict
     * for a valid AndTriplets.
     */
    void givenValidAndTriplets_whenCheckingDynamicTypes_thenIncorrectConflictDetected()
            throws ConceptDoesNotExistException, UnrelatedMappingException,
                    UnsupportedObjectTypeException, ConceptAlreadyExistsException {
        // given
        ConceptManager conceptManager = TestUtils.prepareConceptManager();
        RelationManager relationManager = new RelationManager(conceptManager);

        Variable aggregate = new Variable("aggregate");
        Variable name = new Variable("name");
        Triplet triplet1 =
                new Triplet(
                        aggregate,
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("Aggregate").get());
        Triplet triplet2 =
                new Triplet(aggregate, relationManager.getRelationByName("hasName").get(), name);
        AndTriplets andTriplets = new AndTriplets(Arrays.asList(triplet1, triplet2));

        // when and then
        // Aggregate is a sub-class of FamixClass and thus hasName is defined on it.
        // However, the current dynamicTypeChecker does not look recursively over CustomConcepts
        // until it finds a DefaultConcept.
        Assertions.assertTrue(variableManager.hasConflictingDynamicTypes(andTriplets));
        Assertions.assertTrue(
                variableManager
                        .getVariableByName(aggregate.getName())
                        .get()
                        .hasConflictingDynamicTypes());
        Assertions.assertFalse(
                variableManager
                        .getVariableByName(name.getName())
                        .get()
                        .hasConflictingDynamicTypes());
    }
}
