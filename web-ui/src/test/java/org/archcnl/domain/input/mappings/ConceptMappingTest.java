package org.archcnl.domain.input.mappings;

import static org.junit.Assert.assertTrue;
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
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.CustomConcept;
import org.junit.jupiter.api.Test;

class ConceptMappingTest {

    @Test
    void givenOnionConcepts_whenToStringRepresentation_thenGetExpectedResult()
            throws InvalidVariableNameException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, ConceptDoesNotExistException,
                    VariableAlreadyExistsException, ConceptAlreadyExistsException,
                    UnrelatedMappingException, RelationAlreadyExistsException {
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
            assertEquals(1, mapping.toStringRepresentation().size());
            assertTrue(expected.contains(mapping.toStringRepresentation().get(0)));
        }
    }
}
