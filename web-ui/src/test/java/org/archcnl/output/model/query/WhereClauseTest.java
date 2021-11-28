package org.archcnl.output.model.query;

import java.util.Arrays;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.BooleanValue;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.StringValue;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.output.model.query.WhereClause;
import org.junit.Assert;
import org.junit.Test;

public class WhereClauseTest {

    @Test
    public void givenSimpleWhereClause_whenCallAsFormattedString_thenReturnFormattedString()
            throws InvalidVariableNameException, RelationDoesNotExistException {
        // given
        final Triplet triplet1 =
                new Triplet(
                        new Variable("aggregate"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type"),
                        new CustomConcept("Aggregate", ""));
        final Triplet triplet2 =
                new Triplet(
                        new Variable("aggregate"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("hasName"),
                        new Variable("name"));
        final AndTriplets triplets = new AndTriplets(Arrays.asList(triplet1, triplet2));
        final WhereClause whereClause = new WhereClause(triplets);

        final String expectedQueryString =
                "WHERE {\n"
                        + "  GRAPH ?g {\n"
                        + "    ?aggregate rdf:type architecture:Aggregate.\n"
                        + "    ?aggregate famix:hasName ?name.\n  }\n}";

        // when
        final String whereClauseAsString = whereClause.transformToGui();

        // then
        Assert.assertEquals(expectedQueryString, whereClauseAsString);
    }

    @Test
    public void givenWhereClause_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final Triplet triplet1 =
                new Triplet(
                        new Variable("rule"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type"),
                        new QueryObject("conformance:ArchitectureRule", QueryObjectType.PROPERTY));
        final Triplet triplet2 =
                new Triplet(
                        new Variable("rule"),
                        new QueryPredicate("conformance", "hasRuleRepresentation"),
                        new StringValue("string"));
        final Triplet triplet3 =
                new Triplet(
                        new Variable("rule"),
                        new QueryPredicate("conformance", "hasRuleRepresentation"),
                        new Variable("cnl"));
        final Triplet triplet4 =
                new Triplet(
                        new Variable("violation"),
                        new QueryPredicate("conformance", "violates"),
                        new Variable("rule"));
        final Triplet triplet5 =
                new Triplet(
                        new Variable("proof"),
                        new QueryPredicate("conformance", "proofs"),
                        new Variable("violation"));
        final Triplet triplet6 =
                new Triplet(
                        new Variable("proof"),
                        new QueryPredicate("conformance", "hasNotInferredStatement"),
                        new BooleanValue(true));
        final AndTriplets triplets =
                new AndTriplets(
                        Arrays.asList(triplet1, triplet2, triplet3, triplet4, triplet5, triplet6));
        final WhereClause whereClause = new WhereClause(triplets);

        final String expectedQueryString =
                "WHERE {\n"
                        + "  GRAPH ?g {\n"
                        + "    ?rule rdf:type conformance:ArchitectureRule.\n"
                        + "    ?rule conformance:hasRuleRepresentation \"string\"^^xsd:string.\n"
                        + "    ?rule conformance:hasRuleRepresentation ?cnl.\n"
                        + "    ?violation conformance:violates ?rule.\n"
                        + "    ?proof conformance:proofs ?violation.\n"
                        + "    ?proof conformance:hasNotInferredStatement 'true'^^xsd:boolean.\n"
                        + "  }\n}";

        // when
        final String whereClauseAsString = whereClause.transformToGui();

        // then
        Assert.assertEquals(expectedQueryString, whereClauseAsString);
    }
}
