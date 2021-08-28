package output.model.query;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.output.model.query.Query;
import org.archcnl.output.model.query.QueryNamesapace;
import org.archcnl.output.model.query.SelectClause;
import org.archcnl.output.model.query.WhereClause;
import org.archcnl.output.model.query.WhereTriple;
import org.archcnl.output.model.query.attribute.QueryField;
import org.archcnl.output.model.query.attribute.QueryObject;
import org.archcnl.output.model.query.attribute.QueryObjectType;
import org.archcnl.output.model.query.attribute.QueryPredicate;
import org.junit.Assert;
import org.junit.Test;

public class QueryTest {

  @Test
  public void givenSimpleQuery_whenCallAsFormattedString_thenReturnFormattedQueryString() {
    // given
    final QueryField field = new QueryField("name");
    final Set<QueryField> objects = new LinkedHashSet<>(Arrays.asList(field));
    final SelectClause selectClause = new SelectClause(objects);
    final WhereTriple triple1 = new WhereTriple(new QueryField("aggregate"),
        new QueryPredicate(QueryNamesapace.RDF, "type"),
        new QueryObject("architecture:Aggregate", QueryObjectType.PROPERTY));
    final WhereTriple triple2 = new WhereTriple(new QueryField("aggregate"),
        new QueryPredicate(QueryNamesapace.FAMIX, "hasName"),
        new QueryObject("name", QueryObjectType.FIELD));
    final Set<WhereTriple> triples = new LinkedHashSet<>(Arrays.asList(triple1, triple2));
    final WhereClause whereClause = new WhereClause(triples);
    final Query query = new Query(selectClause, whereClause);

    final String expectedQueryString = "SELECT ?name \n" + "WHERE {\n" + "  GRAPH ?g {\n"
        + "    ?aggregate rdf:type architecture:Aggregate.\n"
        + "    ?aggregate famix:hasName ?name.\n  }\n}";

    // when
    final String queryAsString = query.asFormattedString();

    // then
    Assert.assertEquals(expectedQueryString, queryAsString);
  }

  @Test
  public void givenDifficultQuery_whenCallAsFormattedString_thenReturnFormattedQueryString() {
    // given
    final QueryField field1 = new QueryField("cnl");
    final QueryField field2 = new QueryField("subject");
    final QueryField field3 = new QueryField("predicate");
    final QueryField field4 = new QueryField("object");
    final Set<QueryField> objects =
        new LinkedHashSet<>(Arrays.asList(field1, field2, field3, field4));
    final SelectClause selectClause = new SelectClause(objects);
    final WhereTriple triple1 = new WhereTriple(new QueryField("rule"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "hasRuleRepresentation"), new QueryObject(
            "Every Aggregate must residein a DomainRing.", QueryObjectType.PRIMITIVE_VALUE));
    final WhereTriple triple2 = new WhereTriple(new QueryField("rule"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "hasRuleRepresentation"),
        new QueryObject("cnl", QueryObjectType.FIELD));
    final WhereTriple triple3 = new WhereTriple(new QueryField("violation"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "violates"),
        new QueryObject("rule", QueryObjectType.FIELD));
    final WhereTriple triple4 = new WhereTriple(new QueryField("proof"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "proofs"),
        new QueryObject("violation", QueryObjectType.FIELD));
    final WhereTriple triple5 = new WhereTriple(new QueryField("proof"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "hasNotInferredStatement"),
        new QueryObject("notInferred", QueryObjectType.FIELD));
    final WhereTriple triple6 = new WhereTriple(new QueryField("notInferred"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "hasSubject"),
        new QueryObject("subject", QueryObjectType.FIELD));
    final WhereTriple triple7 = new WhereTriple(new QueryField("notInferred"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "hasPredicate"),
        new QueryObject("predicate", QueryObjectType.FIELD));
    final WhereTriple triple8 = new WhereTriple(new QueryField("notInferred"),
        new QueryPredicate(QueryNamesapace.CONFORMANCE, "hasObject"),
        new QueryObject("object", QueryObjectType.FIELD));
    final Set<WhereTriple> triples = new LinkedHashSet<>(
        Arrays.asList(triple1, triple2, triple3, triple4, triple5, triple6, triple7, triple8));
    final WhereClause whereClause = new WhereClause(triples);
    final Query query = new Query(selectClause, whereClause);

    final String expectedQueryString = "SELECT ?cnl ?subject ?predicate ?object \n" + "WHERE {\n"
        + "  GRAPH ?g {\n"
        + "    ?rule conformance:hasRuleRepresentation \"Every Aggregate must residein a DomainRing.\"^^xsd:string.\n"
        + "    ?rule conformance:hasRuleRepresentation ?cnl.\n"
        + "    ?violation conformance:violates ?rule.\n"
        + "    ?proof conformance:proofs ?violation.\n"
        + "    ?proof conformance:hasNotInferredStatement ?notInferred.\n"
        + "    ?notInferred conformance:hasSubject ?subject.\n"
        + "    ?notInferred conformance:hasPredicate ?predicate.\n"
        + "    ?notInferred conformance:hasObject ?object.\n" + "  }\n" + "}";

    // when
    final String queryAsString = query.asFormattedString();

    // then
    Assert.assertEquals(expectedQueryString, queryAsString);
  }
}
