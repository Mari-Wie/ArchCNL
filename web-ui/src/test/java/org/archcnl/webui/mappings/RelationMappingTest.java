package org.archcnl.webui.mappings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.webui.datatypes.mappings.AndTriplets;
import org.archcnl.webui.datatypes.mappings.ConceptManager;
import org.archcnl.webui.datatypes.mappings.RelationManager;
import org.archcnl.webui.datatypes.mappings.RelationMapping;
import org.archcnl.webui.datatypes.mappings.Triplet;
import org.archcnl.webui.datatypes.mappings.Variable;
import org.archcnl.webui.exceptions.ConceptDoesNotExistException;
import org.archcnl.webui.exceptions.InvalidVariableNameException;
import org.archcnl.webui.exceptions.RecursiveRelationException;
import org.archcnl.webui.exceptions.RelationAlreadyExistsException;
import org.archcnl.webui.exceptions.RelationDoesNotExistException;
import org.archcnl.webui.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.webui.exceptions.VariableAlreadyExistsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RelationMappingTest {

    private static RelationManager relationManager;
    private static ConceptManager conceptManager;

    @BeforeAll
    public static void setUp() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    public void givenOnionRelations_whenToStringRepresentation_thenGetExpectedResult()
            throws RecursiveRelationException, RelationAlreadyExistsException,
                    VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    ConceptDoesNotExistException, InvalidVariableNameException,
                    RelationDoesNotExistException {
        // given
        Variable classVariable = new Variable("class");
        Variable class2Variable = new Variable("class2");
        Variable packageVariable = new Variable("package");
        Variable attributeVariable = new Variable("f");

        // resideIn Mapping
        List<Triplet> triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("Namespace")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("namespaceContains"),
                        classVariable));
        List<AndTriplets> resideInWhenTriplets = new LinkedList<>();
        resideInWhenTriplets.add(new AndTriplets(triplets));

        // use Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        class2Variable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        attributeVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("Attribute")));
        triplets.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("definesAttribute"),
                        attributeVariable));
        triplets.add(
                new Triplet(
                        attributeVariable,
                        relationManager.getRelationByName("hasDeclaredType"),
                        class2Variable));
        List<Triplet> triplets2 = new LinkedList<>();
        triplets2.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets2.add(
                new Triplet(
                        class2Variable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets2.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("imports"),
                        class2Variable));
        List<AndTriplets> useWhenTriplets = new LinkedList<>();
        useWhenTriplets.add(new AndTriplets(triplets));
        useWhenTriplets.add(new AndTriplets(triplets2));

        // when
        RelationMapping resideInMapping =
                new RelationMapping(
                        "resideIn",
                        classVariable,
                        packageVariable,
                        conceptManager.getConceptByName("Namespace"),
                        resideInWhenTriplets);
        List<String> resideInMappingStrings = resideInMapping.toStringRepresentation();

        RelationMapping useMapping =
                new RelationMapping(
                        "use",
                        classVariable,
                        class2Variable,
                        conceptManager.getConceptByName("FamixClass"),
                        useWhenTriplets);
        List<String> useMappingStrings = useMapping.toStringRepresentation();

        // then
        String expectedResideIn =
                "resideInMapping: (?class rdf:type famix:FamixClass)"
                        + " (?package rdf:type famix:Namespace) (?package famix:namespaceContains ?class)"
                        + " -> (?class architecture:resideIn ?package)";
        String expectedUse1 =
                "useMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class2 rdf:type famix:FamixClass) (?f rdf:type famix:Attribute)"
                        + " (?class famix:definesAttribute ?f) (?f famix:hasDeclaredType ?class2)"
                        + " -> (?class architecture:use ?class2)";
        String expectedUse2 =
                "useMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class2 rdf:type famix:FamixClass) (?class famix:imports ?class2)"
                        + " -> (?class architecture:use ?class2)";

        assertEquals(1, resideInMappingStrings.size());
        assertEquals(expectedResideIn, resideInMappingStrings.get(0));
        assertEquals(2, useMappingStrings.size());
        assertEquals(expectedUse1, useMappingStrings.get(0));
        assertEquals(expectedUse2, useMappingStrings.get(1));
    }
}
