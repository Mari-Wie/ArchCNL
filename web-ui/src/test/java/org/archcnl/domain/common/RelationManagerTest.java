package org.archcnl.domain.common;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelationManagerTest {

    private RelationManager relationManager;
    private int inputRelationsCount;
    private int outputRelationsCount;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        relationManager = new RelationManager(new ConceptManager());
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
                        new DefaultRelation("abc", "", new LinkedList<>())));

        // Jena builtin
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new JenaBuiltinRelation("matches", "regex", "", new LinkedList<>())));

        // Famix
        Assertions.assertTrue(
                relationManager.doesRelationExist(new TypeRelation("is-of-type", "type", "")));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasModifier", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasName", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasSignature", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasValue", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasFullQualifiedName", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("isConstructor", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("isExternal", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("isInterface", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasDefiningClass", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasDeclaredException", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasCaughtException", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("throwsException", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasSubClass", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasSuperClass", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesNestedType", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesParameter", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesVariable", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasAnnotationInstance", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasAnnotationType", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasAnnotationTypeAttribute", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation(
                                "hasAnnotationInstanceAttribute", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesAttribute", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesMethod", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("imports", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("namespaceContains", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasDeclaredType", "", new LinkedList<>())));

        // Conformance
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasRuleRepresentation", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasRuleType", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation(
                                "hasNotInferredStatement", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasAssertedStatement", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasSubject", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasPredicate", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasObject", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("proofs", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasDetected", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("hasViolation", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("violates", "", new LinkedList<>())));
        Assertions.assertTrue(
                relationManager.doesRelationExist(
                        new ConformanceRelation("validates", "", new LinkedList<>())));
    }

    @Test
    void givenFreshRelationManager_whenGetRelationByName_thenExpectedResults()
            throws ConceptDoesNotExistException, RelationDoesNotExistException {
        Assertions.assertThrows(
                RelationDoesNotExistException.class,
                () -> {
                    relationManager.getRelationByName("abc");
                });
        Assertions.assertEquals(
                new DefaultRelation("hasModifier", "", new LinkedList<>()),
                relationManager.getRelationByName("hasModifier"));
    }

    @Test
    void givenRelationManager_whenGetRelationByRealName_thenExpectedResults()
            throws RelationDoesNotExistException {
        Assertions.assertThrows(
                RelationDoesNotExistException.class,
                () -> {
                    relationManager.getRelationByRealName("abc");
                });
        Assertions.assertThrows(
                RelationDoesNotExistException.class,
                () -> {
                    relationManager.getRelationByRealName("hasName");
                });
        Assertions.assertEquals(
                new JenaBuiltinRelation("matches", "regex", "", new LinkedList<>()),
                relationManager.getRelationByRealName("regex"));
        Assertions.assertEquals(
                new TypeRelation("is-of-type", "type", ""),
                relationManager.getRelationByRealName("type"));
    }

    @Test
    void givenRelationManager_whenCustomRelationAdded_thenGetCustomRelationsAsExpected()
            throws RelationAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, InvalidVariableNameException {
        Assertions.assertEquals(0, relationManager.getCustomRelations().size());
        relationManager.addRelation(new CustomRelation("test", "", new LinkedList<>()));
        relationManager.addRelation(new DefaultRelation("abc", "", new LinkedList<>()));
        Assertions.assertEquals(1, relationManager.getCustomRelations().size());
    }

    @Test
    void givenRelationManager_whenRelationsAreAdded_thenExpectedResults()
            throws RelationAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, InvalidVariableNameException {
        Assertions.assertEquals(inputRelationsCount, relationManager.getInputRelations().size());
        Assertions.assertEquals(outputRelationsCount, relationManager.getOutputRelations().size());
        relationManager.addRelation(new CustomRelation("test", "", new LinkedList<>()));
        relationManager.addRelation(new DefaultRelation("abc", "", new LinkedList<>()));
        relationManager.addRelation(new TypeRelation("xyz", "xyz", ""));
        relationManager.addRelation(new JenaBuiltinRelation("zhn", "kjh", "", new LinkedList<>()));
        relationManager.addRelation(
                new ConformanceRelation("conformanceRelation", "", new LinkedList<>()));
        Assertions.assertThrows(
                RelationAlreadyExistsException.class,
                () -> {
                    relationManager.addRelation(new CustomRelation("test", "", new LinkedList<>()));
                });
        Assertions.assertEquals(
                inputRelationsCount + 4, relationManager.getInputRelations().size());
        Assertions.assertEquals(
                outputRelationsCount + 4, relationManager.getOutputRelations().size());
    }

    @Test
    void givenRelationManager_whenAddOrAppendIsCalled_thenExpectedResults()
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    InvalidVariableNameException, RelationDoesNotExistException,
                    ConceptDoesNotExistException, RelationAlreadyExistsException,
                    UnrelatedMappingException {
        Assertions.assertEquals(inputRelationsCount, relationManager.getInputRelations().size());
        Assertions.assertEquals(outputRelationsCount, relationManager.getOutputRelations().size());
        relationManager.addOrAppend(new CustomRelation("test", "", new LinkedList<>()));
        relationManager.addOrAppend(new CustomRelation("abc", "", new LinkedList<>()));
        relationManager.addOrAppend(new CustomRelation("abc", "", new LinkedList<>()));
        Assertions.assertEquals(
                inputRelationsCount + 2, relationManager.getInputRelations().size());
        Assertions.assertEquals(
                outputRelationsCount + 2, relationManager.getOutputRelations().size());

        final String relationName = "with";

        final CustomRelation withRelation =
                new CustomRelation(relationName, "", new LinkedList<>());
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
                (CustomRelation) relationManager.getRelationByName(relationName);
        Assertions.assertEquals(
                1, extractedWithRelation.getMapping().get().getWhenTriplets().size());

        final CustomRelation otherWithRelation =
                new CustomRelation(relationName, "", new LinkedList<>());
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
        extractedWithRelation = (CustomRelation) relationManager.getRelationByName(relationName);
        Assertions.assertEquals(
                2, extractedWithRelation.getMapping().get().getWhenTriplets().size());
    }
}
