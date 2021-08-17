package org.archcnl.webui.mappings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import org.archcnl.webui.datatypes.mappings.AndTriplets;
import org.archcnl.webui.datatypes.mappings.ConceptManager;
import org.archcnl.webui.datatypes.mappings.ConceptMapping;
import org.archcnl.webui.datatypes.mappings.RelationManager;
import org.archcnl.webui.datatypes.mappings.Triplet;
import org.archcnl.webui.datatypes.mappings.Variable;
import org.archcnl.webui.exceptions.ConceptAlreadyExistsException;
import org.archcnl.webui.exceptions.ConceptDoesNotExistException;
import org.archcnl.webui.exceptions.InvalidVariableNameException;
import org.archcnl.webui.exceptions.RecursiveRelationException;
import org.archcnl.webui.exceptions.RelationDoesNotExistException;
import org.archcnl.webui.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.webui.exceptions.VariableAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;

public class ConceptMappingTest {

    private RelationManager relationManager;
    private ConceptManager conceptManager;

    @Before
    public void setUp() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    public void givenOnionConcepts_whenToStringRepresentation_thenGetExpectedResult()
            throws RelationDoesNotExistException, ConceptDoesNotExistException,
                    InvalidVariableNameException, RecursiveRelationException,
                    UnsupportedObjectTypeInTriplet, ConceptAlreadyExistsException,
                    VariableAlreadyExistsException {
        // given
        Variable classVariable = new Variable("class");
        Variable nameVariable = new Variable("name");
        Variable packageVariable = new Variable("package");

        // isAggregate Mapping
        List<Triplet> triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        classVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("FamixClass")));
        triplets.add(
                new Triplet(
                        classVariable, relationManager.getRelationByName("hasName"), nameVariable));
        triplets.add(
                new Triplet(
                        nameVariable,
                        relationManager.getRelationByName("matches"),
                        "(\\\\w||\\\\W)*\\\\.(\\\\w||\\\\W)*Aggregate"));
        List<AndTriplets> aggregateWhenTriplets = new LinkedList<>();
        aggregateWhenTriplets.add(new AndTriplets(triplets));

        // isApplicationService Mapping
        triplets = new LinkedList<>();
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
                        relationManager.getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                new Triplet(nameVariable, relationManager.getRelationByName("matches"), "api"));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("namespaceContains"),
                        classVariable));
        List<AndTriplets> applicationServiceWhenTriplets = new LinkedList<>();
        applicationServiceWhenTriplets.add(new AndTriplets(triplets));

        // isDomainRing Mapping
        triplets = new LinkedList<>();
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("is-of-type"),
                        conceptManager.getConceptByName("Namespace")));
        triplets.add(
                new Triplet(
                        packageVariable,
                        relationManager.getRelationByName("hasName"),
                        nameVariable));
        triplets.add(
                new Triplet(nameVariable, relationManager.getRelationByName("matches"), "domain"));
        List<AndTriplets> domainRingWhenTriplets = new LinkedList<>();
        domainRingWhenTriplets.add(new AndTriplets(triplets));

        // when
        ConceptMapping aggregateMapping =
                new ConceptMapping(
                        "Aggregate",
                        classVariable,
                        aggregateWhenTriplets,
                        conceptManager,
                        relationManager);
        List<String> aggregateMappingStrings = aggregateMapping.toStringRepresentation();

        ConceptMapping applicationServiceMapping =
                new ConceptMapping(
                        "ApplicationService",
                        classVariable,
                        applicationServiceWhenTriplets,
                        conceptManager,
                        relationManager);
        List<String> applicationServiceMappingMappingStrings =
                applicationServiceMapping.toStringRepresentation();

        ConceptMapping domainRingMapping =
                new ConceptMapping(
                        "DomainRing",
                        packageVariable,
                        domainRingWhenTriplets,
                        conceptManager,
                        relationManager);
        List<String> domainRingMappingMappingStrings = domainRingMapping.toStringRepresentation();

        // then
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

        assertEquals(1, aggregateMappingStrings.size());
        assertEquals(expectedAggregate, aggregateMappingStrings.get(0));
        assertEquals(1, applicationServiceMappingMappingStrings.size());
        assertEquals(expectedApplicationService, applicationServiceMappingMappingStrings.get(0));
        assertEquals(1, domainRingMappingMappingStrings.size());
        assertEquals(expectedDomainRing, domainRingMappingMappingStrings.get(0));
    }
}
