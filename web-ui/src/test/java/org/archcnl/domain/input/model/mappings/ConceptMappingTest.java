package org.archcnl.domain.input.model.mappings;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.TestUtils;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;
import org.archcnl.domain.common.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.junit.jupiter.api.Test;

class ConceptMappingTest {

    @Test
    void givenConcepts_whenToStringRepresentation_thenGetExpectedResult()
            throws InvalidVariableNameException, UnsupportedObjectTypeException,
                    ConceptDoesNotExistException, VariableAlreadyExistsException,
                    ConceptAlreadyExistsException, UnrelatedMappingException,
                    RelationAlreadyExistsException {
        // given
        List<CustomConcept> customConcepts = TestUtils.prepareCustomConcepts();

        // when
        List<ConceptMapping> actual = new LinkedList<>();
        customConcepts.forEach(
                concept -> concept.getMapping().ifPresent(mapping -> actual.add(mapping)));

        String expectedAggregate =
                "isAggregate: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '(\\\\w||\\\\W)*\\\\.(\\\\w||\\\\W)*Aggregate')"
                        + " -> (?class rdf:type architecture:Aggregate)";
        String expectedApplicationService =
                "isApplicationService: (?class rdf:type famix:FamixClass)"
                        + " (?package rdf:type famix:Namespace) (?package famix:hasName ?name)"
                        + " regex(?name, 'api') (?package famix:namespaceContains ?class)"
                        + " -> (?class rdf:type architecture:ApplicationService)";
        String expectedDomainRing =
                "isDomainRing: (?package rdf:type famix:Namespace)"
                        + " (?package famix:hasName ?name) regex(?name, 'domain')"
                        + " -> (?package rdf:type architecture:DomainRing)";

        String expectedEmptyWhen =
                "isEmptyWhenConcept: -> (?var rdf:type architecture:EmptyWhenConcept)";

        List<String> expected = new LinkedList<>();
        expected.add(expectedAggregate);
        expected.add(expectedApplicationService);
        expected.add(expectedDomainRing);
        expected.add(expectedEmptyWhen);

        // then
        assertEquals(4, actual.size());
        for (ConceptMapping mapping : actual) {
            assertEquals(1, mapping.transformToAdoc().size());
            assertTrue(expected.contains(mapping.transformToAdoc().get(0)));
        }
    }
}
