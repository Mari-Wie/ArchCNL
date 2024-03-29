package org.archcnl.stardogwrapper.api;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.archcnl.stardogwrapper.api.exceptions.NoConnectionToStardogServerException;

public interface StardogDatabaseAPI {
    public class Result {
        List<String> vars = new ArrayList<String>();
        ArrayList<ArrayList<String>> violations = new ArrayList<ArrayList<String>>();

        public Result(List<String> variables) {
            vars = variables;
        }

        public ArrayList<ArrayList<String>> getViolations() {
            return violations;
        }

        public List<String> getVars() {
            return vars;
        }

        public void add(ArrayList<String> violation) {
            violations.add(violation);
        }

        public int getNumberOfViolations() {
            return violations.size();
        }

        public boolean isEmpty() {
            return violations.isEmpty();
        }
    }
    /**
     * Connects to the database using an admin connection if not already connected. Uses the server,
     * database, user name, and password returned by <code>getServer()</code>, <code>
     * getDatabaseName()</code>, <code>getUserName()</code>, and <code>getPassword()</code>. If the
     * database does not exist, a new one with the given name will be created.
     *
     * @param deletePreviousDatabases If all previous databases should be deleted
     */
    void connect(boolean deletePreviousDatabases);

    /** Disconnects from the database. */
    void closeConnectionToServer();

    /** PROTOTYPE Executes a stardog select query on the open connection\ */
    Optional<Result> executeSelectQuery(String query);

    /**
     * Retrieves an ontology from the database which matches the given RDF context. The ontology is
     * written to the specified file as an OWL file in XML format.
     *
     * @param context The URI of the context. Stardog stores the data in a named RDF graph. The
     *     context is the name of this graph.
     * @param path The path to the file to which the retrieved model will be written.
     */
    void writeModelFromContextToFile(String context, String path);

    /**
     * Adds namespaces to the database, so that results of queries on the database have shortened
     * names according to the defined namespaces.
     *
     * @param nsMap A map of prefixes with their iris
     */
    void addNamespaces(Map<String, String> nsMap);

    /** @return the server's name */
    String getServer();

    /** @return the database's name */
    String getDatabaseName();

    /** @return the user name */
    String getUserName();

    /** @return the user's password */
    String getPassword();

    /**
     * Loads the given RDF file (in XML format) and stores it in the database under the given RDF
     * context. Must be connected a database. Note: RDF contexts are an extension of the core RDF
     * model.
     *
     * @param pathToData Path to the RDF file in XML format which contains the ontology that will be
     *     stored in the database.
     * @param context The URI of the context to use.
     * @throws FileNotFoundException When the input file cannot be accessed.
     * @throws NoConnectionToStardogServerException When not connected to Stardog.
     */
    void addDataByRDFFileAsNamedGraph(String pathToData, String context)
            throws FileNotFoundException, NoConnectionToStardogServerException;
}
