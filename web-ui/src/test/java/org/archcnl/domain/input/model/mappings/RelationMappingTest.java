package org.archcnl.domain.input.model.mappings;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;
import org.archcnl.domain.common.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.junit.jupiter.api.Test;

class RelationMappingTest {

    @Test
    void givenRelations_whenToStringRepresentation_thenGetExpectedResult()
            throws UnsupportedObjectTypeException, ConceptDoesNotExistException,
                    ConceptAlreadyExistsException, UnrelatedMappingException,
                    RelationAlreadyExistsException {
        // given
        List<CustomRelation> customRelations = TestUtils.prepareCustomRelations();

        // when
        Map<String, RelationMapping> actual = new HashMap<>();
        for (CustomRelation relation : customRelations) {
            if (relation.getMapping().isPresent()) {
                actual.put(relation.getName(), relation.getMapping().get());
            }
        }

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
        assertEquals(1, actual.get("resideIn").transformToAdoc().size());
        assertEquals(expectedResideIn, actual.get("resideIn").transformToAdoc().get(0));
        assertEquals(2, actual.get("use").transformToAdoc().size());
        assertTrue(actual.get("use").transformToAdoc().contains(expectedUse1));
        assertTrue(actual.get("use").transformToAdoc().contains(expectedUse2));
        assertEquals(1, actual.get("emptyWhenRelationVariable").transformToAdoc().size());
        assertEquals(
                expectedEmptyVariableMapping,
                actual.get("emptyWhenRelationVariable").transformToAdoc().get(0));
        assertEquals(1, actual.get("emptyWhenRelationString").transformToAdoc().size());
        assertEquals(
                expectedEmptyStringMapping,
                actual.get("emptyWhenRelationString").transformToAdoc().get(0));
        assertEquals(1, actual.get("emptyWhenRelationBoolean").transformToAdoc().size());
        assertEquals(
                expectedEmptyBooleanMapping,
                actual.get("emptyWhenRelationBoolean").transformToAdoc().get(0));
    }
}
