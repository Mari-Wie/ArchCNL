package org.archcnl.output.model.query;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.domain.output.model.query.SelectClause;
import org.archcnl.domain.output.model.query.WhereClause;
import org.archcnl.domain.output.model.query.WhereTriple;
import org.archcnl.domain.output.model.query.attribute.QueryField;
import org.archcnl.domain.output.model.query.attribute.QueryNamespace;
import org.archcnl.domain.output.model.query.attribute.QueryObject;
import org.archcnl.domain.output.model.query.attribute.QueryObjectType;
import org.archcnl.domain.output.model.query.attribute.QueryPredicate;
import org.junit.Assert;
import org.junit.Test;

public class QueryTest {

    @Test
    public void givenSimpleQuery_whenCallAsFormattedString_thenReturnFormattedQueryString() {
        // given
        final QueryField field = new QueryField("name");
        final Set<QueryField> objects = new LinkedHashSet<>(Arrays.asList(field));
        final SelectClause selectClause = new SelectClause(objects);

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
        final Query query = new Query(getDefaultNamespaces(), selectClause, whereClause);

        final String expectedQueryString =
                "SELECT ?name \n"
                        + "WHERE {\n"
                        + "  GRAPH ?g {\n"
                        + "    ?aggregate rdf:type architecture:Aggregate.\n"
                        + "    ?aggregate famix:hasName ?name.\n  }\n}";

        // when
        final String queryAsString = query.asFormattedString();

        // then
        Assert.assertEquals(
                getDefaultNamespacesAsFormattedString() + "\n" + expectedQueryString,
                queryAsString);
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
        final WhereTriple triple1 =
                new WhereTriple(
                        new QueryField("rule"),
                        new QueryPredicate("conformance", "hasRuleRepresentation"),
                        new QueryObject(
                                "Every Aggregate must residein a DomainRing.",
                                QueryObjectType.PRIMITIVE_VALUE));
        final WhereTriple triple2 =
                new WhereTriple(
                        new QueryField("rule"),
                        new QueryPredicate("conformance", "hasRuleRepresentation"),
                        new QueryObject("cnl", QueryObjectType.FIELD));
        final WhereTriple triple3 =
                new WhereTriple(
                        new QueryField("violation"),
                        new QueryPredicate("conformance", "violates"),
                        new QueryObject("rule", QueryObjectType.FIELD));
        final WhereTriple triple4 =
                new WhereTriple(
                        new QueryField("proof"),
                        new QueryPredicate("conformance", "proofs"),
                        new QueryObject("violation", QueryObjectType.FIELD));
        final WhereTriple triple5 =
                new WhereTriple(
                        new QueryField("proof"),
                        new QueryPredicate("conformance", "hasNotInferredStatement"),
                        new QueryObject("notInferred", QueryObjectType.FIELD));
        final WhereTriple triple6 =
                new WhereTriple(
                        new QueryField("notInferred"),
                        new QueryPredicate("conformance", "hasSubject"),
                        new QueryObject("subject", QueryObjectType.FIELD));
        final WhereTriple triple7 =
                new WhereTriple(
                        new QueryField("notInferred"),
                        new QueryPredicate("conformance", "hasPredicate"),
                        new QueryObject("predicate", QueryObjectType.FIELD));
        final WhereTriple triple8 =
                new WhereTriple(
                        new QueryField("notInferred"),
                        new QueryPredicate("conformance", "hasObject"),
                        new QueryObject("object", QueryObjectType.FIELD));
        final Set<WhereTriple> triples =
                new LinkedHashSet<>(
                        Arrays.asList(
                                triple1, triple2, triple3, triple4, triple5, triple6, triple7,
                                triple8));
        final WhereClause whereClause = new WhereClause(triples);
        final Query query = new Query(getDefaultNamespaces(), selectClause, whereClause);

        final String expectedQueryString =
                "SELECT ?cnl ?subject ?predicate ?object \n"
                        + "WHERE {\n"
                        + "  GRAPH ?g {\n"
                        + "    ?rule conformance:hasRuleRepresentation \"Every Aggregate must residein a DomainRing.\"^^xsd:string.\n"
                        + "    ?rule conformance:hasRuleRepresentation ?cnl.\n"
                        + "    ?violation conformance:violates ?rule.\n"
                        + "    ?proof conformance:proofs ?violation.\n"
                        + "    ?proof conformance:hasNotInferredStatement ?notInferred.\n"
                        + "    ?notInferred conformance:hasSubject ?subject.\n"
                        + "    ?notInferred conformance:hasPredicate ?predicate.\n"
                        + "    ?notInferred conformance:hasObject ?object.\n"
                        + "  }\n"
                        + "}";

        // when
        final String queryAsString = query.asFormattedString();

        // then
        Assert.assertEquals(
                getDefaultNamespacesAsFormattedString() + "\n" + expectedQueryString,
                queryAsString);
    }

    @Test
    public void givenSimpleQuery_whenCallAsFormattedQuery_thenReturnFormattedQueryString() {
        // given
        final QueryField field = new QueryField("name");
        final Set<QueryField> objects = new LinkedHashSet<>(Arrays.asList(field));
        final SelectClause selectClause = new SelectClause(objects);
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
        final Query query = new Query(getDefaultNamespaces(), selectClause, whereClause);

        final String expectedQueryString =
                "SELECT ?name "
                        + "WHERE { GRAPH ?g { ?aggregate rdf:type architecture:Aggregate. "
                        + "?aggregate famix:hasName ?name. }}";

        // when
        final String queryAsString = query.asFormattedQuery();

        // then
        Assert.assertEquals(
                getDefaultNamespacesAsFormattedQuery() + " " + expectedQueryString, queryAsString);
    }

    private Set<QueryNamespace> getDefaultNamespaces() {
        final QueryNamespace rdf =
                new QueryNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns");
        final QueryNamespace owl = new QueryNamespace("owl", "http://www.w3.org/2002/07/owl");
        final QueryNamespace rdfs =
                new QueryNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema");
        final QueryNamespace xsd = new QueryNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
        final QueryNamespace conformance =
                new QueryNamespace(
                        "conformance", "http://arch-ont.org/ontologies/architectureconformance");
        final QueryNamespace famix =
                new QueryNamespace("famix", "http://arch-ont.org/ontologies/famix.owl");
        final QueryNamespace architecture =
                new QueryNamespace(
                        "architecture", "http://www.arch-ont.org/ontologies/architecture.owl");
        return new LinkedHashSet<>(
                Arrays.asList(rdf, owl, rdfs, xsd, conformance, famix, architecture));
    }

    private String getDefaultNamespacesAsFormattedString() {
        return "PREFIX rdf <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX owl <http://www.w3.org/2002/07/owl#>\n"
                + "PREFIX rdfs <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX xsd <http://www.w3.org/2001/XMLSchema#>\n"
                + "PREFIX conformance <http://arch-ont.org/ontologies/architectureconformance#>\n"
                + "PREFIX famix <http://arch-ont.org/ontologies/famix.owl#>\n"
                + "PREFIX architecture <http://www.arch-ont.org/ontologies/architecture.owl#>";
    }

    private String getDefaultNamespacesAsFormattedQuery() {
        return "PREFIX rdf <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX owl <http://www.w3.org/2002/07/owl#> "
                + "PREFIX rdfs <http://www.w3.org/2000/01/rdf-schema#> "
                + "PREFIX xsd <http://www.w3.org/2001/XMLSchema#> "
                + "PREFIX conformance <http://arch-ont.org/ontologies/architectureconformance#> "
                + "PREFIX famix <http://arch-ont.org/ontologies/famix.owl#> "
                + "PREFIX architecture <http://www.arch-ont.org/ontologies/architecture.owl#>";
    }
}