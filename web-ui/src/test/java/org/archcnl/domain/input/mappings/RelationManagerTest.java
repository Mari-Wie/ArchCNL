package org.archcnl.domain.input.mappings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.domain.common.DefaultRelation;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.SpecialRelation;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.TypeRelation;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelationManagerTest {

    private RelationManager relationManager;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        relationManager = new RelationManager(new ConceptManager());
    }

    @Test
    void givenRelationManager_whenCreated_thenExpectedRelations() {
        assertEquals(28, relationManager.getRelations().size());
        assertFalse(
                relationManager.doesRelationExist(new DefaultRelation("abc", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new SpecialRelation("matches", "regex", new LinkedList<>())));
        assertTrue(relationManager.doesRelationExist(new TypeRelation("is-of-type", "type")));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasModifier", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasName", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasSignature", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasValue", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasFullQualifiedName", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("isConstructor", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("isExternal", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("isInterface", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasDefiningClass", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasDeclaredException", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasCaughtException", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("throwsException", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasSubClass", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasSuperClass", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesNestedType", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesParameter", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesVariable", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasAnnotationInstance", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasAnnotationType", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasAnnotationTypeAttribute", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasAnnotationInstanceAttribute", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesAttribute", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("definesMethod", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("imports", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("namespaceContains", new LinkedList<>())));
        assertTrue(
                relationManager.doesRelationExist(
                        new DefaultRelation("hasDeclaredType", new LinkedList<>())));
    }

    @Test
    void givenFreshRelationManager_whenGetRelationByName_thenExpectedResults()
            throws ConceptDoesNotExistException, RelationDoesNotExistException {
        assertThrows(
                RelationDoesNotExistException.class,
                () -> {
                    relationManager.getRelationByName("abc");
                });
        assertEquals(
                new DefaultRelation("hasModifier", new LinkedList<>()),
                relationManager.getRelationByName("hasModifier"));
    }

    @Test
    void givenRelationManager_whenGetRelationByRealName_thenExpectedResults()
            throws RelationDoesNotExistException {
        assertThrows(
                RelationDoesNotExistException.class,
                () -> {
                    relationManager.getRelationByRealName("abc");
                });
        assertThrows(
                RelationDoesNotExistException.class,
                () -> {
                    relationManager.getRelationByRealName("hasName");
                });
        assertEquals(
                new SpecialRelation("matches", "regex", new LinkedList<>()),
                relationManager.getRelationByRealName("regex"));
        assertEquals(
                new TypeRelation("is-of-type", "type"),
                relationManager.getRelationByRealName("type"));
    }

    @Test
    void givenReelationManager_whenCustomRelationAdded_thenGetCustomRelationsAsExpected()
            throws RelationAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, InvalidVariableNameException {
        assertEquals(0, relationManager.getCustomRelations().size());
        relationManager.addRelation(new CustomRelation("test", new LinkedList<>()));
        relationManager.addRelation(new DefaultRelation("abc", new LinkedList<>()));
        assertEquals(1, relationManager.getCustomRelations().size());
    }

    @Test
    void givenRelationManager_whenRelationsAreAdded_thenExpectedResults()
            throws RelationAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, InvalidVariableNameException {
        assertEquals(28, relationManager.getRelations().size());
        relationManager.addRelation(new CustomRelation("test", new LinkedList<>()));
        relationManager.addRelation(new DefaultRelation("abc", new LinkedList<>()));
        relationManager.addRelation(new TypeRelation("xyz", "xyz"));
        relationManager.addRelation(new SpecialRelation("zhn", "kjh", new LinkedList<>()));
        assertThrows(
                RelationAlreadyExistsException.class,
                () -> {
                    relationManager.addRelation(new CustomRelation("test", new LinkedList<>()));
                });
        assertEquals(32, relationManager.getRelations().size());
    }

    @Test
    void givenRelationManager_whenAddOrAppendIsCalled_thenExpectedResults()
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    InvalidVariableNameException, RelationDoesNotExistException,
                    ConceptDoesNotExistException, RelationAlreadyExistsException,
                    UnrelatedMappingException {
        assertEquals(28, relationManager.getRelations().size());
        relationManager.addOrAppend(new CustomRelation("test", new LinkedList<>()));
        relationManager.addOrAppend(new CustomRelation("abc", new LinkedList<>()));
        relationManager.addOrAppend(new CustomRelation("abc", new LinkedList<>()));
        assertEquals(30, relationManager.getRelations().size());

        String relationName = "with";

        CustomRelation withRelation = new CustomRelation(relationName, new LinkedList<>());
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
        RelationMapping mapping1 =
                new RelationMapping(
                        TripletFactory.createTriplet(
                                new Variable("class"), withRelation, new Variable("x")),
                        when1);
        withRelation.setMapping(mapping1);
        relationManager.addOrAppend(withRelation);
        assertEquals(31, relationManager.getRelations().size());

        CustomRelation extractedWithRelation =
                (CustomRelation) relationManager.getRelationByName(relationName);
        assertEquals(1, extractedWithRelation.getMapping().get().getWhenTriplets().size());

        CustomRelation otherWithRelation = new CustomRelation(relationName, new LinkedList<>());
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
        RelationMapping mapping2 =
                new RelationMapping(
                        TripletFactory.createTriplet(
                                new Variable("class"), otherWithRelation, new Variable("x")),
                        when2);
        otherWithRelation.setMapping(mapping2);
        relationManager.addOrAppend(otherWithRelation);
        assertEquals(31, relationManager.getRelations().size());
        extractedWithRelation = (CustomRelation) relationManager.getRelationByName(relationName);
        assertEquals(2, extractedWithRelation.getMapping().get().getWhenTriplets().size());
    }
}
