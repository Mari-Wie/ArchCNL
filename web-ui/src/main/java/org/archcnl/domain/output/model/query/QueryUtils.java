package org.archcnl.domain.output.model.query;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.output.model.query.attribute.QueryNamespace;

public class QueryUtils {

    private QueryUtils() {}

    public static Query getDefaultQuery()
            throws UnsupportedObjectTypeInTriplet, InvalidVariableNameException,
                    NoSuchElementException {

        RelationManager relationManager =
                RulesConceptsAndRelations.getInstance().getRelationManager();
        ConceptManager conceptManager = RulesConceptsAndRelations.getInstance().getConceptManager();

        Variable cnl = new Variable("cnl");
        Variable violation = new Variable("violation");
        Variable subject = new Variable("subject");
        Variable rule = new Variable("rule");
        Variable name = new Variable("name");
        Variable statement = new Variable("statement");
        Variable proof = new Variable("proof");

        Triplet triplet1 =
                TripletFactory.createTriplet(
                        rule,
                        relationManager.getRelationByName("is-of-type").get(),
                        conceptManager.getConceptByName("ArchitectureRule").get());
        Triplet triplet2 =
                TripletFactory.createTriplet(
                        rule,
                        relationManager.getRelationByName("hasRuleRepresentation").get(),
                        cnl);
        Triplet triplet3 =
                TripletFactory.createTriplet(
                        violation, relationManager.getRelationByName("violates").get(), rule);
        Triplet triplet4 =
                TripletFactory.createTriplet(
                        proof, relationManager.getRelationByName("proofs").get(), violation);
        Triplet triplet5 =
                TripletFactory.createTriplet(
                        proof,
                        relationManager.getRelationByName("hasAssertedStatement").get(),
                        statement);
        Triplet triplet6 =
                TripletFactory.createTriplet(
                        statement, relationManager.getRelationByName("hasSubject").get(), subject);
        Triplet triplet7 =
                TripletFactory.createTriplet(
                        subject, relationManager.getRelationByName("hasName").get(), name);

        SelectClause selectClause = new SelectClause(new LinkedHashSet<>(Arrays.asList(cnl, name)));

        WhereClause whereClause =
                new WhereClause(
                        new AndTriplets(
                                new LinkedList<>(
                                        Arrays.asList(
                                                triplet1, triplet2, triplet3, triplet4, triplet5,
                                                triplet6, triplet7))));

        return new Query(getNamespaces(), selectClause, whereClause);
    }

    public static Set<QueryNamespace> getNamespaces() {
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
        final QueryNamespace main =
                new QueryNamespace("main", "http://arch-ont.org/ontologies/main.owl");
        return new LinkedHashSet<>(
                Arrays.asList(rdf, owl, rdfs, xsd, conformance, famix, architecture, main));
    }
}
