package org.archcnl.toolchain;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.impl.StardogDatabase;
import org.junit.Test;
import org.junit.Assert;

public class ToolchainIT {

	private static final String FIRST_RULE_VIOLATED_QUERY = "queries/firstRuleViolated.sparql";
    private static final String FIRST_RULE_CORRECTLY_MAPPED_QUERY = "queries/firstRuleCorrectlyMapped.sparql";
    private static final String SECOND_RULE_VIOLATED_QUERY = "queries/secondRuleViolated.sparql";
    private static final String SECOND_RULE_CORRECTLY_MAPPED_QUERY = "queries/secondRuleCorrectlyMapped.sparql";
    private final String rootDir = "./src/integration-test/resources/";
    private final String database = "archcnl_it_db";
    private final String server = "http://localhost:5820";
    private final String username = "admin";
    private final String password = "admin";
    private final String context = "http://graphs.org/" + database + "/1.0";
    private final String resultPath = rootDir + "result.owl";
    private final List<String> sourcePaths = Arrays.asList(rootDir + "OnionArchitectureDemo/src/");
    private final String ruleFile = rootDir + "OnionArchitectureDemo/rules.adoc";
    private final boolean verboseLogging = false;
    private final boolean removeDBs = true;
    private final List<String> enabledParsers = Arrays.asList("java");
    
    @Test
    public void givenArchitecture_whenRunningToolchain_thenRuleResultsAreCorrect() throws IOException {
    	// given, when
        CNLToolchain.runToolchain(database,
                server,
                context,
                username,
                password,
                sourcePaths,
                ruleFile, 
                verboseLogging, 
                removeDBs,
                enabledParsers);
        
        
        writeResultToFile();
        
        OntModel result = loadResult();
        // then
        Assert.assertTrue(askQueryResult(result, FIRST_RULE_VIOLATED_QUERY));
        Assert.assertTrue(askQueryResult(result, SECOND_RULE_VIOLATED_QUERY));
        Assert.assertTrue(askQueryResult(result, FIRST_RULE_CORRECTLY_MAPPED_QUERY));
        Assert.assertTrue(askQueryResult(result, SECOND_RULE_CORRECTLY_MAPPED_QUERY));
    }

    private OntModel loadResult() throws IOException {
        // Note: Stardog's context is the name of the RDF graph in which the ontology
        // is stored. During the writing from DB/loading into RAM steps, this database name
        // is lost. All data stored in result is an unnamed graph.
        // Thus, "GRAPH ?g {}" must not be included in the SPARQL queries executed on result.
        // WHERE {GRAPH ?g { ... }} works only on named graphs, while WHERE { ... } works only
        // on the default graph. Thus, the queries in the SPARQL-queries directory (which are supposed to
        // be executed on the DB directly) use GRAPH and the queries used by this test do not.
        OntModel result = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        result.read(resultPath);
        return result;
    }

    private void writeResultToFile() {
        StardogDatabaseAPI db = new StardogDatabase(server, database, username, password);
        db.connect(false);
        db.writeModelFromContextToFile(context, resultPath);
        db.closeConnectionToServer();
    }
    
    private boolean askQueryResult(OntModel model, String path) throws IOException {
        return execAskQuery(Path.of(rootDir + path), model);
    }
    
    private boolean execAskQuery(Path queryPath, OntModel model) throws IOException {
        final String queryString = Files.readString(queryPath, StandardCharsets.UTF_8);
        Query query = QueryFactory.create(queryString) ;
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        boolean result = qexec.execAsk();
        qexec.close();
        return result;
    }

}
