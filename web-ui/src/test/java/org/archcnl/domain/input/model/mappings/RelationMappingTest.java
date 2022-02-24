package org.archcnl.domain.input.model.mappings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.junit.jupiter.api.Test;

class RelationMappingTest {

    @Test
    void givenRelations_whenToStringRepresentation_thenGetExpectedResult()
            throws InvalidVariableNameException, UnsupportedObjectTypeException,
                    ConceptDoesNotExistException, VariableAlreadyExistsException,
                    ConceptAlreadyExistsException, UnrelatedMappingException,
                    RelationAlreadyExistsException {
        // given
        List<CustomRelation> customRelations = TestUtils.prepareCustomRelations();

        // when
        List<RelationMapping> actual = new LinkedList<>();
        customRelations.forEach(
                relation -> relation.getMapping().ifPresent(mapping -> actual.add(mapping)));

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

        String expectedEmptyStringMapping =
                "emptyWhenRelationStringMapping: -> (?var architecture:emptyWhenRelationString 'test string')";

        String expectedEmptyBooleanMapping =
                "emptyWhenRelationBooleanMapping: -> (?var architecture:emptyWhenRelationBoolean 'false'^^xsd:boolean)";

        String expectedEmptyVariableMapping =
                "emptyWhenRelationVariableMapping: -> (?var architecture:emptyWhenRelationVariable ?test)";

        // then
        assertEquals(5, actual.size());
        assertEquals(1, actual.get(0).transformToAdoc().size());
        assertEquals(expectedResideIn, actual.get(0).transformToAdoc().get(0));
        assertEquals(2, actual.get(1).transformToAdoc().size());
        assertEquals(expectedUse1, actual.get(1).transformToAdoc().get(0));
        assertEquals(expectedUse2, actual.get(1).transformToAdoc().get(1));
        assertEquals(1, actual.get(2).transformToAdoc().size());
        assertEquals(expectedEmptyVariableMapping, actual.get(2).transformToAdoc().get(0));
        assertEquals(1, actual.get(3).transformToAdoc().size());
        assertEquals(expectedEmptyStringMapping, actual.get(3).transformToAdoc().get(0));
        assertEquals(1, actual.get(4).transformToAdoc().size());
        assertEquals(expectedEmptyBooleanMapping, actual.get(4).transformToAdoc().get(0));
    }
}
