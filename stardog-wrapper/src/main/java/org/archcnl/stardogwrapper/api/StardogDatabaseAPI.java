package org.archcnl.stardogwrapper.api;

import java.io.FileNotFoundException;
import org.archcnl.stardogwrapper.api.exceptions.NoConnectionToStardogServerException;

public interface StardogDatabaseAPI {

    /**
     * Connects to the database using an admin connection if not already connected. Uses the server,
     * database, user name, and password returned by <code>getServer()</code>, <code>
     * getDatabaseName()</code>, <code>getUserName()</code>, and <code>getPassword()</code>. If the
     * database does not exist, a new one with the given name will be created.
     *
     * @param deletePreviousDatabases If all previous databases should be deleted
     */
    void connect(boolean deletePreviousDatabases);

    /** PROTOTYPE Executes a stardog select query on the open connection\ */
    void executeSelectQuery(String query);

    /** Disconnects from the database. */
    void closeConnectionToServer();

    /**
     * Retrieves an ontology from the database which matches the given RDF context. The ontology is
     * written to the specified file as an OWL file in XML format.
     *
     * @param context The URI of the context. Stardog stores the data in a named RDF graph. The
     *     context is the name of this graph.
     * @param path The path to the file to which the retrieved model will be written.
     */
    void writeModelFromContextToFile(String context, String path);

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
