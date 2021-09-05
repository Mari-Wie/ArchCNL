package org.archcnl.webui.mappings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.archcnl.domain.input.datatypes.RulesConceptsAndRelations;
import org.archcnl.domain.input.datatypes.mappings.AndTriplets;
import org.archcnl.domain.input.datatypes.mappings.ConceptManager;
import org.archcnl.domain.input.datatypes.mappings.CustomRelation;
import org.archcnl.domain.input.datatypes.mappings.DefaultRelation;
import org.archcnl.domain.input.datatypes.mappings.RelationManager;
import org.archcnl.domain.input.datatypes.mappings.RelationMapping;
import org.archcnl.domain.input.datatypes.mappings.SpecialRelation;
import org.archcnl.domain.input.datatypes.mappings.Triplet;
import org.archcnl.domain.input.datatypes.mappings.TypeRelation;
import org.archcnl.domain.input.datatypes.mappings.Variable;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
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
        assertEquals(27, relationManager.getRelations().size());
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
        relationManager.addRelation(new CustomRelation("test"));
        relationManager.addRelation(new DefaultRelation("abc", new LinkedList<>()));
        assertEquals(1, relationManager.getCustomRelations().size());
    }

    @Test
    void givenRelationManager_whenRelationsAreAdded_thenExpectedResults()
            throws RelationAlreadyExistsException, VariableAlreadyExistsException,
                    UnsupportedObjectTypeInTriplet, InvalidVariableNameException {
        assertEquals(27, relationManager.getRelations().size());
        relationManager.addRelation(new CustomRelation("test"));
        relationManager.addRelation(new DefaultRelation("abc", new LinkedList<>()));
        relationManager.addRelation(new TypeRelation("xyz", "xyz"));
        relationManager.addRelation(new SpecialRelation("zhn", "kjh", new LinkedList<>()));
        assertThrows(
                RelationAlreadyExistsException.class,
                () -> {
                    relationManager.addRelation(new CustomRelation("test"));
                });
        assertEquals(31, relationManager.getRelations().size());
    }

    @Test
    void givenRelationManager_whenAddOrAppendIsCalled_thenExpectedResults()
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    InvalidVariableNameException, RelationDoesNotExistException,
                    ConceptDoesNotExistException, RelationAlreadyExistsException,
                    UnrelatedMappingException {
        assertEquals(27, relationManager.getRelations().size());
        relationManager.addOrAppend(new CustomRelation("test"));
        relationManager.addOrAppend(new CustomRelation("abc"));
        relationManager.addOrAppend(new CustomRelation("abc"));
        assertEquals(29, relationManager.getRelations().size());

        CustomRelation XYZ1 = new CustomRelation("withMapping");
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
        RelationMapping mapping1 =
                new RelationMapping(new Variable("class"), new Variable("x"), when1, XYZ1);
        XYZ1.setMapping(mapping1);
        relationManager.addOrAppend(XYZ1);
        assertEquals(30, relationManager.getRelations().size());
        CustomRelation XYZ_extracted =
                (CustomRelation) relationManager.getRelationByName("withMapping");
        assertEquals(1, XYZ_extracted.getMapping().getWhenTriplets().size());

        CustomRelation XYZ2 = new CustomRelation("withMapping");
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
        RelationMapping mapping2 =
                new RelationMapping(new Variable("class"), new Variable("x"), when2, XYZ2);
        XYZ2.setMapping(mapping2);
        relationManager.addOrAppend(XYZ2);
        assertEquals(30, relationManager.getRelations().size());
        XYZ_extracted = (CustomRelation) relationManager.getRelationByName("withMapping");
        assertEquals(2, XYZ_extracted.getMapping().getWhenTriplets().size());
    }
}
