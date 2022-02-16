package org.archcnl.domain.common;

import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.TripletFactory;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TripletTest {

    private ConceptManager conceptManager;
    private RelationManager relationManager;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    void givenTripletInput_whenCallTripletFactory_thenExpectedResults()
            throws RelationDoesNotExistException, InvalidVariableNameException,
                    UnsupportedObjectTypeInTriplet {
        // given
        final Variable subject = new Variable("name");
        final Relation predicate = relationManager.getRelationByName("matches").get();
        final ObjectType validObjectType1 = new Variable("pattern");
        final ObjectType validObjectType2 = new StringValue("someString");
        final ObjectType invalidObjectType = new BooleanValue(true);

        // when
        final Triplet triplet1 = TripletFactory.createTriplet(subject, predicate, validObjectType1);
        final Triplet triplet2 = TripletFactory.createTriplet(subject, predicate, validObjectType2);

        // then
        Assertions.assertEquals(subject, triplet1.getSubject());
        Assertions.assertEquals(subject, triplet2.getSubject());
        Assertions.assertEquals(predicate, triplet1.getPredicate());
        Assertions.assertEquals(predicate, triplet2.getPredicate());
        Assertions.assertEquals(validObjectType1, triplet1.getObject());
        Assertions.assertEquals(validObjectType2, triplet2.getObject());
        Assertions.assertThrows(
                UnsupportedObjectTypeInTriplet.class,
                () -> TripletFactory.createTriplet(subject, predicate, invalidObjectType));
    }

    @Test
    void givenWhereTripletWithVariable_whenCallAsFormattedString_thenReturnFormattedString()
            throws InvalidVariableNameException, RelationDoesNotExistException {
        // given
        final Triplet triplet =
                new Triplet(
                        new Variable("aggregate"),
                        relationManager.getRelationByName("hasName").get(),
                        new Variable("name"));

        final String expectedGuiString = "?aggregate famix:hasName ?name.";
        final String expectedQueryString = "?aggregate famix:hasName ?name.";
        final String expectedAdocString = "(?aggregate famix:hasName ?name)";

        // when
        final String tripletAsGuiString = triplet.transformToGui();
        final String tripletAsQueryString = triplet.transformToSparqlQuery();
        final String tripletAsAdocString = triplet.transformToAdoc();

        // then
        Assertions.assertEquals(expectedGuiString, tripletAsGuiString);
        Assertions.assertEquals(expectedQueryString, tripletAsQueryString);
        Assertions.assertEquals(expectedAdocString, tripletAsAdocString);
    }

    @Test
    void givenWhereTripletWithStringValue_whenCallAsFormattedString_thenReturnFormattedString()
            throws InvalidVariableNameException, RelationDoesNotExistException {
        // given
        final Triplet triplet =
                new Triplet(
                        new Variable("aggregate"),
                        relationManager.getRelationByName("hasName").get(),
                        new StringValue("Some string value"));

        final String expectedGuiString = "?aggregate famix:hasName 'Some string value'.";
        final String expectedQueryString =
                "?aggregate famix:hasName \"Some string value\"^^xsd:string.";
        final String expectedAdocString = "(?aggregate famix:hasName 'Some string value')";

        // when
        final String tripletAsGuiString = triplet.transformToGui();
        final String tripletAsQueryString = triplet.transformToSparqlQuery();
        final String tripletAsAdocString = triplet.transformToAdoc();

        // then
        Assertions.assertEquals(expectedGuiString, tripletAsGuiString);
        Assertions.assertEquals(expectedQueryString, tripletAsQueryString);
        Assertions.assertEquals(expectedAdocString, tripletAsAdocString);
    }

    @Test
    void givenWhereTripleWithBooleanValue_whenCallAsFormattedString_thenReturnFormattedString()
            throws InvalidVariableNameException, RelationDoesNotExistException {
        // given
        final Triplet triplet =
                new Triplet(
                        new Variable("class"),
                        relationManager.getRelationByName("isInterface").get(),
                        new BooleanValue(true));

        final String expectedGuiString = "?class famix:isInterface 'true'^^xsd:boolean.";
        final String expectedQueryString = "?class famix:isInterface \"true\"^^xsd:boolean.";
        final String expectedAdocString = "(?class famix:isInterface 'true'^^xsd:boolean)";

        // when
        final String tripletAsGuiString = triplet.transformToGui();
        final String tripletAsQueryString = triplet.transformToSparqlQuery();
        final String tripletAsAdocString = triplet.transformToAdoc();

        // then
        Assertions.assertEquals(expectedGuiString, tripletAsGuiString);
        Assertions.assertEquals(expectedQueryString, tripletAsQueryString);
        Assertions.assertEquals(expectedAdocString, tripletAsAdocString);
    }

    @Test
    void givenWhereTripleWithConcept_whenCallAsFormattedString_thenReturnFormattedString()
            throws InvalidVariableNameException, RelationDoesNotExistException,
                    ConceptDoesNotExistException {
        // given
        final Triplet triplet =
                new Triplet(
                        new Variable("class"),
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("FamixClass").get());

        final String expectedGuiString = "?class rdf:type famix:FamixClass.";
        final String expectedQueryString = "?class rdf:type famix:FamixClass.";
        final String expectedAdocString = "(?class rdf:type famix:FamixClass)";

        // when
        final String tripletAsGuiString = triplet.transformToGui();
        final String tripletAsQueryString = triplet.transformToSparqlQuery();
        final String tripletAsAdocString = triplet.transformToAdoc();

        // then
        Assertions.assertEquals(expectedGuiString, tripletAsGuiString);
        Assertions.assertEquals(expectedQueryString, tripletAsQueryString);
        Assertions.assertEquals(expectedAdocString, tripletAsAdocString);
    }
}
