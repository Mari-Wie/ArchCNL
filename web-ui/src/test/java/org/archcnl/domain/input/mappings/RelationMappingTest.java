package org.archcnl.domain.input.mappings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.input.TestUtils;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.input.model.mappings.CustomRelation;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.junit.jupiter.api.Test;

class RelationMappingTest {

    @Test
    void givenOnionRelations_whenToStringRepresentation_thenGetExpectedResult()
            throws InvalidVariableNameException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, ConceptDoesNotExistException,
                    VariableAlreadyExistsException, ConceptAlreadyExistsException,
                    UnrelatedMappingException, RelationAlreadyExistsException {
        // given
        List<CustomRelation> customRelations = TestUtils.prepareCustomRelations();

        // when
        List<RelationMapping> actual = new LinkedList<>();
        customRelations.forEach((relation) -> actual.add(relation.getMapping()));

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

        // then
        assertEquals(2, actual.size());
        assertEquals(1, actual.get(0).toStringRepresentation().size());
        assertEquals(expectedResideIn, actual.get(0).toStringRepresentation().get(0));
        assertEquals(2, actual.get(1).toStringRepresentation().size());
        assertEquals(expectedUse1, actual.get(1).toStringRepresentation().get(0));
        assertEquals(expectedUse2, actual.get(1).toStringRepresentation().get(1));
    }
}
