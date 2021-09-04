package org.archcnl.output.model.query;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.domain.output.model.query.WhereClause;
import org.archcnl.domain.output.model.query.WhereTriple;
import org.archcnl.domain.output.model.query.attribute.QueryField;
import org.archcnl.domain.output.model.query.attribute.QueryObject;
import org.archcnl.domain.output.model.query.attribute.QueryObjectType;
import org.archcnl.domain.output.model.query.attribute.QueryPredicate;
import org.junit.Assert;
import org.junit.Test;

public class WhereClauseTest {

    @Test
    public void givenSimpleWhereClause_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final WhereTriple triple1 =
                new WhereTriple(
                        new QueryField("aggregate"),
                        new QueryPredicate("rdf", "type"),
                        new QueryObject("architecture:Aggregate", QueryObjectType.PROPERTY));
        final WhereTriple triple2 =
                new WhereTriple(
                        new QueryField("aggregate"),
                        new QueryPredicate("famix", "hasName"),
                        new QueryObject("name", QueryObjectType.FIELD));
        final Set<WhereTriple> triples = new LinkedHashSet<>(Arrays.asList(triple1, triple2));
        final WhereClause whereClause = new WhereClause(triples);

        final String expectedQueryString =
                "WHERE {\n"
                        + "  GRAPH ?g {\n"
                        + "    ?aggregate rdf:type architecture:Aggregate.\n"
                        + "    ?aggregate famix:hasName ?name.\n  }\n}";

        // when
        final String whereClauseAsString = whereClause.asFormattedString();

        // then
        Assert.assertEquals(expectedQueryString, whereClauseAsString);
    }

    @Test
    public void givenWhereClause_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final WhereTriple triple1 =
                new WhereTriple(
                        new QueryField("rule"),
                        new QueryPredicate("rdf", "type"),
                        new QueryObject("conformance:ArchitectureRule", QueryObjectType.PROPERTY));
        final WhereTriple triple2 =
                new WhereTriple(
                        new QueryField("rule"),
                        new QueryPredicate("conformance", "hasRuleRepresentation"),
                        new QueryObject("1", QueryObjectType.PRIMITIVE_VALUE));
        final WhereTriple triple3 =
                new WhereTriple(
                        new QueryField("rule"),
                        new QueryPredicate("conformance", "hasRuleRepresentation"),
                        new QueryObject("cnl", QueryObjectType.FIELD));
        final WhereTriple triple4 =
                new WhereTriple(
                        new QueryField("violation"),
                        new QueryPredicate("conformance", "violates"),
                        new QueryObject("rule", QueryObjectType.FIELD));
        final WhereTriple triple5 =
                new WhereTriple(
                        new QueryField("proof"),
                        new QueryPredicate("conformance", "proofs"),
                        new QueryObject("violation", QueryObjectType.FIELD));
        final WhereTriple triple6 =
                new WhereTriple(
                        new QueryField("proof"),
                        new QueryPredicate("conformance", "hasNotInferredStatement"),
                        new QueryObject("1.1", QueryObjectType.PRIMITIVE_VALUE));
        final Set<WhereTriple> triples =
                new LinkedHashSet<>(
                        Arrays.asList(triple1, triple2, triple3, triple4, triple5, triple6));
        final WhereClause whereClause = new WhereClause(triples);

        final String expectedQueryString =
                "WHERE {\n"
                        + "  GRAPH ?g {\n"
                        + "    ?rule rdf:type conformance:ArchitectureRule.\n"
                        + "    ?rule conformance:hasRuleRepresentation \"1\"^^xsd:integer.\n"
                        + "    ?rule conformance:hasRuleRepresentation ?cnl.\n"
                        + "    ?violation conformance:violates ?rule.\n"
                        + "    ?proof conformance:proofs ?violation.\n"
                        + "    ?proof conformance:hasNotInferredStatement \"1.1\"^^xsd:double.\n"
                        + "  }\n}";

        // when
        final String whereClauseAsString = whereClause.asFormattedString();

        // then
        Assert.assertEquals(expectedQueryString, whereClauseAsString);
    }
}
