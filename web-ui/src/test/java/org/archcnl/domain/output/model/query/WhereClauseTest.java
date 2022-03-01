package org.archcnl.domain.output.model.query;

import java.util.Arrays;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WhereClauseTest {

    private ConceptManager conceptManager;
    private RelationManager relationManager;

    @Before
    public void setup() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    public void givenSimpleWhereClause_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final Triplet triplet1 =
                new Triplet(
                        new Variable("aggregate"),
                        relationManager.getRelationByName("is-of-type").get(),
                        new CustomConcept("Aggregate", ""));
        final Triplet triplet2 =
                new Triplet(
                        new Variable("aggregate"),
                        relationManager.getRelationByName("hasName").get(),
                        new Variable("name"));
        final AndTriplets triplets = new AndTriplets(Arrays.asList(triplet1, triplet2));
        final WhereClause whereClause = new WhereClause(triplets);

        final String expectedQueryString =
                "WHERE {"
                        + System.lineSeparator()
                        + "  GRAPH ?g {"
                        + System.lineSeparator()
                        + "    ?aggregate rdf:type architecture:Aggregate."
                        + System.lineSeparator()
                        + "    ?aggregate famix:hasName ?name."
                        + System.lineSeparator()
                        + "  }"
                        + System.lineSeparator()
                        + "}";

        // when
        final String whereClauseAsString = whereClause.transformToGui();

        // then
        Assert.assertEquals(expectedQueryString, whereClauseAsString);
    }

    @Test
    public void givenWhereClause_whenCallAsFormattedString_thenReturnFormattedString()
            throws ConceptDoesNotExistException {
        // given
        final Triplet triplet1 =
                new Triplet(
                        new Variable("rule"),
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("ArchitectureRule").get());
        final Triplet triplet2 =
                new Triplet(
                        new Variable("rule"),
                        relationManager.getRelationByName("hasRuleRepresentation").get(),
                        new StringValue("string"));
        final Triplet triplet3 =
                new Triplet(
                        new Variable("rule"),
                        relationManager.getRelationByName("hasRuleRepresentation").get(),
                        new Variable("cnl"));
        final Triplet triplet4 =
                new Triplet(
                        new Variable("violation"),
                        relationManager.getRelationByName("violates").get(),
                        new Variable("rule"));
        final Triplet triplet5 =
                new Triplet(
                        new Variable("proof"),
                        relationManager.getRelationByName("proofs").get(),
                        new Variable("violation"));
        final Triplet triplet6 =
                new Triplet(
                        new Variable("proof"),
                        relationManager.getRelationByName("hasNotInferredStatement").get(),
                        new BooleanValue(true));
        final AndTriplets triplets =
                new AndTriplets(
                        Arrays.asList(triplet1, triplet2, triplet3, triplet4, triplet5, triplet6));
        final WhereClause whereClause = new WhereClause(triplets);

        final String expectedQueryString =
                "WHERE {"
                        + System.lineSeparator()
                        + "  GRAPH ?g {"
                        + System.lineSeparator()
                        + "    ?rule rdf:type conformance:ArchitectureRule."
                        + System.lineSeparator()
                        + "    ?rule conformance:hasRuleRepresentation 'string'."
                        + System.lineSeparator()
                        + "    ?rule conformance:hasRuleRepresentation ?cnl."
                        + System.lineSeparator()
                        + "    ?violation conformance:violates ?rule."
                        + System.lineSeparator()
                        + "    ?proof conformance:proofs ?violation."
                        + System.lineSeparator()
                        + "    ?proof conformance:hasNotInferredStatement 'true'^^xsd:boolean."
                        + System.lineSeparator()
                        + "  }"
                        + System.lineSeparator()
                        + "}";

        // when
        final String whereClauseAsString = whereClause.transformToGui();

        // then
        Assert.assertEquals(expectedQueryString, whereClauseAsString);
    }
}
