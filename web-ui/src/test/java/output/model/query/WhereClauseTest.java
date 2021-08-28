package output.model.query;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.output.model.query.QueryNamesapace;
import org.archcnl.output.model.query.WhereClause;
import org.archcnl.output.model.query.WhereTriple;
import org.archcnl.output.model.query.attribute.QueryField;
import org.archcnl.output.model.query.attribute.QueryObject;
import org.archcnl.output.model.query.attribute.QueryObjectType;
import org.archcnl.output.model.query.attribute.QueryPredicate;
import org.junit.Assert;
import org.junit.Test;

public class WhereClauseTest {

  @Test
  public void givenSimpleWhereClause_whenCallAsFormattedString_thenReturnFormattedString() {
    // given
    final WhereTriple triple1 = new WhereTriple(new QueryField("aggregate"),
        new QueryPredicate(QueryNamesapace.RDF, "type"),
        new QueryObject("architecture:Aggregate", QueryObjectType.PROPERTY));
    final WhereTriple triple2 = new WhereTriple(new QueryField("aggregate"),
        new QueryPredicate(QueryNamesapace.FAMIX, "hasName"),
        new QueryObject("name", QueryObjectType.FIELD));
    final Set<WhereTriple> triples = new LinkedHashSet<>(Arrays.asList(triple1, triple2));
    final WhereClause whereClause = new WhereClause(triples);

    final String expectedQueryString =
        "WHERE {\n" + "  GRAPH ?g {\n" + "    ?aggregate rdf:type architecture:Aggregate.\n"
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
        new WhereTriple(new QueryField("rule"), new QueryPredicate(QueryNamesapace.RDF, "type"),
            new QueryObject("conformance:ArchitectureRule", QueryObjectType.PROPERTY));
    final WhereTriple triple2 = new WhereTriple(new QueryField("rule"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "hasRuleRepresentation"),
        new QueryObject("1", QueryObjectType.PRIMITIVE_VALUE));
    final WhereTriple triple3 = new WhereTriple(new QueryField("rule"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "hasRuleRepresentation"),
        new QueryObject("cnl", QueryObjectType.FIELD));
    final WhereTriple triple4 = new WhereTriple(new QueryField("violation"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "violates"),
        new QueryObject("rule", QueryObjectType.FIELD));
    final WhereTriple triple5 = new WhereTriple(new QueryField("proof"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "proofs"),
        new QueryObject("violation", QueryObjectType.FIELD));
    final WhereTriple triple6 = new WhereTriple(new QueryField("proof"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "hasNotInferredStatement"),
        new QueryObject("1.1", QueryObjectType.PRIMITIVE_VALUE));
    final Set<WhereTriple> triples =
        new LinkedHashSet<>(Arrays.asList(triple1, triple2, triple3, triple4, triple5, triple6));
    final WhereClause whereClause = new WhereClause(triples);

    final String expectedQueryString =
        "WHERE {\n" + "  GRAPH ?g {\n" + "    ?rule rdf:type conformance:ArchitectureRule.\n"
            + "    ?rule conformance:hasRuleRepresentation \"1\"^^xsd:integer.\n"
            + "    ?rule conformance:hasRuleRepresentation ?cnl.\n"
            + "    ?violation conformance:violates ?rule.\n"
            + "    ?proof conformance:proofs ?violation.\n"
            + "    ?proof conformance:hasNotInferredStatement \"1.1\"^^xsd:double.\n" + "  }\n}";

    // when
    final String whereClauseAsString = whereClause.asFormattedString();

    // then
    Assert.assertEquals(expectedQueryString, whereClauseAsString);
  }
}
