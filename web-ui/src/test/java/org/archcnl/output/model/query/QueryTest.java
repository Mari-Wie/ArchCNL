package org.archcnl.output.model.query;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.domain.output.model.query.SelectClause;
import org.archcnl.domain.output.model.query.WhereClause;
import org.archcnl.domain.output.model.query.attribute.QueryNamespace;
import org.junit.Assert;
import org.junit.Test;

public class QueryTest {

    @Test
    public void givenSimpleQuery_whenCallAsFormattedString_thenReturnFormattedQueryString()
            throws InvalidVariableNameException, RelationDoesNotExistException {
        // given
        final Variable field = new Variable("name");
        final Set<Variable> objects = new LinkedHashSet<>(Arrays.asList(field));
        final SelectClause selectClause = new SelectClause(objects);

        final Triplet triplet1 =
                new Triplet(
                        new Variable("aggregate"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type")
                                .get(),
                        new CustomConcept("Aggregate", ""));
        final Triplet triplet2 =
                new Triplet(
                        new Variable("aggregate"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("hasName")
                                .get(),
                        new Variable("name"));
        final AndTriplets triplets = new AndTriplets(Arrays.asList(triplet1, triplet2));
        final WhereClause whereClause = new WhereClause(triplets);
        final Query query = new Query(getDefaultNamespaces(), selectClause, whereClause);

        final String expectedQueryString =
                "SELECT ?name "
                        + System.lineSeparator()
                        + "WHERE {"
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
        final String queryAsString = query.transformToGui();

        // then
        Assert.assertEquals(
                getDefaultNamespacesAsFormattedString() + expectedQueryString, queryAsString);
    }

    @Test
    public void givenDifficultQuery_whenCallAsFormattedString_thenReturnFormattedQueryString()
            throws InvalidVariableNameException, RelationDoesNotExistException {
        // given
        final Variable field1 = new Variable("cnl");
        final Variable field2 = new Variable("subject");
        final Variable field3 = new Variable("predicate");
        final Variable field4 = new Variable("object");
        final Set<Variable> objects =
                new LinkedHashSet<>(Arrays.asList(field1, field2, field3, field4));
        final SelectClause selectClause = new SelectClause(objects);
        final Triplet triplet1 =
                new Triplet(
                        new Variable("rule"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("hasRuleRepresentation")
                                .get(),
                        new StringValue("Every Aggregate must residein a DomainRing."));
        final Triplet triplet2 =
                new Triplet(
                        new Variable("rule"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("hasRuleRepresentation")
                                .get(),
                        new Variable("cnl"));
        final Triplet triplet3 =
                new Triplet(
                        new Variable("violation"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("violates")
                                .get(),
                        new Variable("rule"));
        final Triplet triplet4 =
                new Triplet(
                        new Variable("proof"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("proofs")
                                .get(),
                        new Variable("violation"));
        final Triplet triplet5 =
                new Triplet(
                        new Variable("proof"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("hasNotInferredStatement")
                                .get(),
                        new Variable("notInferred"));
        final Triplet triplet6 =
                new Triplet(
                        new Variable("notInferred"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("hasSubject")
                                .get(),
                        new Variable("subject"));
        final Triplet triplet7 =
                new Triplet(
                        new Variable("notInferred"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("hasPredicate")
                                .get(),
                        new Variable("predicate"));
        final Triplet triplet8 =
                new Triplet(
                        new Variable("notInferred"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("hasObject")
                                .get(),
                        new Variable("object"));
        final AndTriplets triplets =
                new AndTriplets(
                        Arrays.asList(
                                triplet1, triplet2, triplet3, triplet4, triplet5, triplet6,
                                triplet7, triplet8));
        final WhereClause whereClause = new WhereClause(triplets);
        final Query query = new Query(getDefaultNamespaces(), selectClause, whereClause);

        final String expectedQueryString =
                "SELECT ?cnl ?subject ?predicate ?object "
                        + System.lineSeparator()
                        + "WHERE {"
                        + System.lineSeparator()
                        + "  GRAPH ?g {"
                        + System.lineSeparator()
                        + "    ?rule conformance:hasRuleRepresentation 'Every Aggregate must residein a DomainRing.'."
                        + System.lineSeparator()
                        + "    ?rule conformance:hasRuleRepresentation ?cnl."
                        + System.lineSeparator()
                        + "    ?violation conformance:violates ?rule."
                        + System.lineSeparator()
                        + "    ?proof conformance:proofs ?violation."
                        + System.lineSeparator()
                        + "    ?proof conformance:hasNotInferredStatement ?notInferred."
                        + System.lineSeparator()
                        + "    ?notInferred conformance:hasSubject ?subject."
                        + System.lineSeparator()
                        + "    ?notInferred conformance:hasPredicate ?predicate."
                        + System.lineSeparator()
                        + "    ?notInferred conformance:hasObject ?object."
                        + System.lineSeparator()
                        + "  }"
                        + System.lineSeparator()
                        + "}";

        // when
        final String queryAsString = query.transformToGui();

        // then
        Assert.assertEquals(
                getDefaultNamespacesAsFormattedString() + expectedQueryString, queryAsString);
    }

    @Test
    public void givenSimpleQuery_whenCallAsFormattedQuery_thenReturnFormattedQueryString()
            throws InvalidVariableNameException, RelationDoesNotExistException {
        // given
        final Variable field = new Variable("name");
        final Set<Variable> objects = new LinkedHashSet<>(Arrays.asList(field));
        final SelectClause selectClause = new SelectClause(objects);
        final Triplet triplet1 =
                new Triplet(
                        new Variable("aggregate"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("is-of-type")
                                .get(),
                        new CustomConcept("Aggregate", ""));
        final Triplet triplet2 =
                new Triplet(
                        new Variable("aggregate"),
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName("hasName")
                                .get(),
                        new Variable("name"));
        final AndTriplets triplets = new AndTriplets(Arrays.asList(triplet1, triplet2));
        final WhereClause whereClause = new WhereClause(triplets);
        final Query query = new Query(getDefaultNamespaces(), selectClause, whereClause);

        final String expectedQueryString =
                "SELECT ?name "
                        + "WHERE { GRAPH ?g { ?aggregate rdf:type architecture:Aggregate. "
                        + "?aggregate famix:hasName ?name. }}";

        // when
        final String queryAsString = query.transformToSparqlQuery();

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
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + System.lineSeparator()
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + System.lineSeparator()
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + System.lineSeparator()
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + System.lineSeparator()
                + "PREFIX conformance: <http://arch-ont.org/ontologies/architectureconformance#>"
                + System.lineSeparator()
                + "PREFIX famix: <http://arch-ont.org/ontologies/famix.owl#>"
                + System.lineSeparator()
                + "PREFIX architecture: <http://www.arch-ont.org/ontologies/architecture.owl#>"
                + System.lineSeparator();
    }

    private String getDefaultNamespacesAsFormattedQuery() {
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
                + "PREFIX conformance: <http://arch-ont.org/ontologies/architectureconformance#> "
                + "PREFIX famix: <http://arch-ont.org/ontologies/famix.owl#> "
                + "PREFIX architecture: <http://www.arch-ont.org/ontologies/architecture.owl#>";
    }
}
