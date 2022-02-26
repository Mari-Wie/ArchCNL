package org.archcnl.domain.common;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.ConformanceRelation;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.FamixRelation;
import org.archcnl.domain.common.conceptsandrelations.JenaBuiltinRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.TripletFactory;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelationManagerTest {

    private ConceptManager conceptManager;
    private RelationManager relationManager;
    private int inputRelationsCount;
    private int outputRelationsCount;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
        inputRelationsCount = 28;
        outputRelationsCount = 39;
    }

    @Test
    void givenRelationManager_whenGetInputRelations_thenExpectedResults() {
        // given and when
        final List<Relation> relations = relationManager.getInputRelations();

        // then
        Assertions.assertEquals(inputRelationsCount, relations.size());
        Assertions.assertFalse(relations.stream().anyMatch(ConformanceRelation.class::isInstance));
    }

    @Test
    void givenRelationManager_whenGetOutputRelations_thenExpectedResults() {
        // given and when
        final List<Relation> relations = relationManager.getOutputRelations();

        // then
        Assertions.assertEquals(outputRelationsCount, relations.size());
        Assertions.assertFalse(relations.stream().anyMatch(JenaBuiltinRelation.class::isInstance));
    }

    @Test
    void givenRelationManager_whenCreated_thenExpectedRelations() {
        Assertions.assertEquals(inputRelationsCount, relationManager.getInputRelations().size());
        Assertions.assertEquals(outputRelationsCount, relationManager.getOutputRelations().size());
        Assertions.assertFalse(
                relationManager.doesRelationExist(
                        new FamixRelation("abc", "", new LinkedHashSet<>())));

        // Jena builtin
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new JenaBuiltinRelation("matches", "regex", "", new LinkedHashSet<>())));

        // Famix
        Assertions.assertTrue(relationManager.doesRelationExist(TypeRelation.getTyperelation()));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasModifier", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasName", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasSignature", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasValue", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasFullQualifiedName", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("isConstructor", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("isExternal", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("isInterface", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasDefiningClass", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasDeclaredException", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasCaughtException", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("throwsException", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasSubClass", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasSuperClass", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("definesNestedType", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("definesParameter", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("definesVariable", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasAnnotationInstance", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasAnnotationType", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation(
                                "hasAnnotationTypeAttribute", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation(
                                "hasAnnotationInstanceAttribute", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("definesAttribute", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("definesMethod", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("imports", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("namespaceContains", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new FamixRelation("hasDeclaredType", "", new LinkedHashSet<>())));

        // Conformance
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation(
                                "hasRuleRepresentation", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasRuleType", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation(
                                "hasNotInferredStatement", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation(
                                "hasAssertedStatement", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasSubject", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasPredicate", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasObject", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("proofs", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasDetected", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasViolation", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("violates", "", new LinkedHashSet<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("validates", "", new LinkedHashSet<>())));
    }

    @Test
    void givenFreshRelationManager_whenGetRelationByName_thenExpectedResults()
            throws ConceptDoesNotExistException {
        Assertions.assertEquals(Optional.empty(), relationManager.getRelationByName("abc"));
        Assertions.assertEquals(
                Optional.of(new FamixRelation("hasModifier", "", new LinkedHashSet<>())),
                relationManager.getRelationByName("hasModifier"));
    }

    @Test
    void givenRelationManager_whenGetRelationByRealName_thenExpectedResults() {
        Assertions.assertEquals(Optional.empty(), relationManager.getRelationByRealName("abc"));
        Assertions.assertEquals(Optional.empty(), relationManager.getRelationByRealName("hasName"));
        Assertions.assertEquals(
                Optional.of(new JenaBuiltinRelation("matches", "regex", "", new LinkedHashSet<>())),
                relationManager.getRelationByRealName("regex"));
        Assertions.assertEquals(
                Optional.of(TypeRelation.getTyperelation()),
                relationManager.getRelationByRealName("type"));
    }

    @Test
    void givenRelationManager_whenCustomRelationAdded_thenGetCustomRelationsAsExpected()
            throws RelationAlreadyExistsException, UnsupportedObjectTypeException {
        Assertions.assertEquals(0, relationManager.getCustomRelations().size());
        relationManager.addRelation(new CustomRelation("test", "", new LinkedHashSet<>()));
        relationManager.addRelation(new FamixRelation("abc", "", new LinkedHashSet<>()));
        Assertions.assertEquals(1, relationManager.getCustomRelations().size());
    }

    @Test
    void givenRelationManager_whenRelationsAreAdded_thenExpectedResults()
            throws RelationAlreadyExistsException, UnsupportedObjectTypeException {
        Assertions.assertEquals(inputRelationsCount, relationManager.getInputRelations().size());
        Assertions.assertEquals(outputRelationsCount, relationManager.getOutputRelations().size());
        relationManager.addRelation(new CustomRelation("test", "", new LinkedHashSet<>()));
        relationManager.addRelation(new FamixRelation("abc", "", new LinkedHashSet<>()));
        relationManager.addRelation(
                new JenaBuiltinRelation("zhn", "kjh", "", new LinkedHashSet<>()));
        relationManager.addRelation(
                new ConformanceRelation("conformanceRelation", "", new LinkedHashSet<>()));
        Assertions.assertThrows(
                RelationAlreadyExistsException.class,
                () -> {
                    relationManager.addRelation(
                            new CustomRelation("test", "", new LinkedHashSet<>()));
                });
        Assertions.assertThrows(
                RelationAlreadyExistsException.class,
                () -> {
                    relationManager.addRelation(TypeRelation.getTyperelation());
                });
        Assertions.assertEquals(
                inputRelationsCount + 3, relationManager.getInputRelations().size());
        Assertions.assertEquals(
                outputRelationsCount + 3, relationManager.getOutputRelations().size());
    }

    @Test
    void givenRelationManager_whenAddOrAppendIsCalled_thenExpectedResults()
            throws UnsupportedObjectTypeException, ConceptDoesNotExistException,
                    RelationAlreadyExistsException, UnrelatedMappingException {
        Assertions.assertEquals(inputRelationsCount, relationManager.getInputRelations().size());
        Assertions.assertEquals(outputRelationsCount, relationManager.getOutputRelations().size());
        relationManager.addOrAppend(new CustomRelation("test", "", new LinkedHashSet<>()));
        relationManager.addOrAppend(new CustomRelation("abc", "", new LinkedHashSet<>()));
        relationManager.addOrAppend(new CustomRelation("abc", "", new LinkedHashSet<>()));
        Assertions.assertEquals(
                inputRelationsCount + 2, relationManager.getInputRelations().size());
        Assertions.assertEquals(
                outputRelationsCount + 2, relationManager.getOutputRelations().size());

        final String relationName = "with";

        final CustomRelation withRelation =
                new CustomRelation(relationName, "", new LinkedHashSet<>());
        final List<AndTriplets> when1 = new LinkedList<>();
        final List<Triplet> and1 = new LinkedList<>();
        and1.add(
                TripletFactory.createTriplet(
                        new Variable("class"),
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("FamixClass").get()));
        when1.add(new AndTriplets(and1));
        final RelationMapping mapping1 =
                new RelationMapping(
                        TripletFactory.createTriplet(
                                new Variable("class"), withRelation, new Variable("x")),
                        when1);
        withRelation.setMapping(mapping1);
        relationManager.addOrAppend(withRelation);
        Assertions.assertEquals(
                inputRelationsCount + 3, relationManager.getInputRelations().size());
        Assertions.assertEquals(
                outputRelationsCount + 3, relationManager.getOutputRelations().size());

        CustomRelation extractedWithRelation =
                (CustomRelation) relationManager.getRelationByName(relationName).get();
        Assertions.assertEquals(
                1, extractedWithRelation.getMapping().get().getWhenTriplets().size());

        final CustomRelation otherWithRelation =
                new CustomRelation(relationName, "", new LinkedHashSet<>());
        final List<AndTriplets> when2 = new LinkedList<>();
        final List<Triplet> and2 = new LinkedList<>();
        and2.add(
                TripletFactory.createTriplet(
                        new Variable("class"),
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("Enum").get()));
        when2.add(new AndTriplets(and2));
        final RelationMapping mapping2 =
                new RelationMapping(
                        TripletFactory.createTriplet(
                                new Variable("class"), otherWithRelation, new Variable("x")),
                        when2);
        otherWithRelation.setMapping(mapping2);
        relationManager.addOrAppend(otherWithRelation);
        Assertions.assertEquals(
                inputRelationsCount + 3, relationManager.getInputRelations().size());
        Assertions.assertEquals(
                outputRelationsCount + 3, relationManager.getOutputRelations().size());
        extractedWithRelation =
                (CustomRelation) relationManager.getRelationByName(relationName).get();
        Assertions.assertEquals(
                2, extractedWithRelation.getMapping().get().getWhenTriplets().size());
    }
}
