package org.archcnl.stardogwrapper.impl;

import com.complexible.common.rdf.query.resultio.TextTableQueryResultWriter;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.ConnectionPool;
import com.complexible.stardog.api.ConnectionPoolConfig;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.stardog.stark.Resource;
import com.stardog.stark.Value;
import com.stardog.stark.Values;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import com.stardog.stark.query.io.QueryResultWriters;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.exceptions.NoConnectionToStardogServerException;

public class StardogDatabase implements StardogDatabaseAPI {

    private static final Logger LOG = LogManager.getLogger(StardogDatabase.class);

    private String _server;
    private String _databaseName;

    private String _userName;
    private String _password;

    private ConnectionPool connectionPool;
    private Connection connection;

    public StardogDatabase(String server, String databaseName, String userName, String password) {
        _server = server;
        _databaseName = databaseName;
        _userName = userName;
        _password = password;
    }

    @Override
    public void connect(boolean deletePreviousDatabases) {
        LOG.info("Connecting to the database ....");
        if (connectionPool != null || connection != null) {
            LOG.debug("Closing existing Connections");
            closeConnectionToServer();
        }

        try (final AdminConnection aConn =
                AdminConnectionConfiguration.toServer(_server)
                        .credentials(_userName, _password)
                        .connect()) {

            if (deletePreviousDatabases) {
                LOG.debug("Deleting previous databases");
                for (String databaseName : aConn.list()) {
                    aConn.drop(databaseName);
                }
            }

            if (!aConn.list().contains(_databaseName)) {
                aConn.newDatabase(_databaseName).create();
                LOG.info("New database created: " + _databaseName);
            } else {
                LOG.warn("Database already exists: " + _databaseName);
            }
        }

        LOG.debug("Start connection configuration ...");
        ConnectionConfiguration connectionConfig =
                ConnectionConfiguration.to(_databaseName)
                        .server(_server)
                        .reasoning(false)
                        .credentials(_userName, _password);
        ConnectionPoolConfig poolConfig = ConnectionPoolConfig.using(connectionConfig);
        connectionPool = poolConfig.create();
        LOG.debug("ConnectionPool created.");

        connection = connectionPool.obtain();
        LOG.debug("Connection obtained.");

        LOG.info("Connection established.");
    }

    @Override
    public void closeConnectionToServer() {

        releaseConnection();
        connectionPool.shutdown();

        connectionPool = null;
        connection = null;
    }

    private void releaseConnection() {
        try {
            connectionPool.release(connection);
        } catch (StardogException e) {
            LOG.error("CONNECTION ERROR");
            e.printStackTrace();
        }
    }

    @Override
    public void writeModelFromContextToFile(String context, String path) {
        try {
            LOG.debug("Retrieving ontology from the database, storing it in a file: " + path);
            Resource resource = Values.iri(context);
            connection
                    .export()
                    .context(resource)
                    .format(RDFFormats.RDFXML)
                    .to(new FileOutputStream(new File(path)));
        } catch (StardogException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            LOG.error("CONNECTION ERROR: " + e.getClass());
            LOG.error("CONNECTION ERROR: File not found or no connection to database");
            e.printStackTrace();
        }
    }

    @Override
    public void addNamespaces(Map<String, String> nsMap) {
        for (String prefix : nsMap.keySet()) {
            connection.namespaces().add(prefix, nsMap.get(prefix));
        }
    }

    @Override
    public String getServer() {
        return _server;
    }

    @Override
    public String getDatabaseName() {
        return _databaseName;
    }

    @Override
    public String getUserName() {
        return _userName;
    }

    @Override
    public String getPassword() {
        return _password;
    }

    @Override
    public void addDataByRDFFileAsNamedGraph(String pathToData, String context)
            throws FileNotFoundException, NoConnectionToStardogServerException {
        if (connection == null) {
            LOG.error("CONNECTION ERROR: No connection.");
            throw new NoConnectionToStardogServerException();
        }

        LOG.debug("Adding ontology from file: " + pathToData);
        Resource namedGraph = Values.iri(context);
        connection.begin();
        // declare the transaction
        connection.add().io().context(namedGraph).format(RDFFormats.RDFXML).stream(
                new FileInputStream(pathToData));
        // and commit the change
        connection.commit();
        LOG.info("Change committed.");
    }

    private Result extractResults(String query) {
        SelectQuery stardogSelectQuery = connection.select(query);
        // Try block should close the connection
        try (SelectQueryResult queryResults = stardogSelectQuery.execute()) {

            List<String> variables = queryResults.variables();
            Result queryResult = new Result(variables);

            while (queryResults.hasNext()) {

                // Here we get the result from the query
                BindingSet stardogResult = queryResults.next();

                ArrayList<String> singleResult = new ArrayList<String>();
                for (String variableName : variables) {
                    // Extracting a String from the stardog result BindingSet
                    Value value = stardogResult.get(variableName);
                    if (value != null) {
                        // Result is not shortened according to prefixes (like it would be on
                        // stardog studio) and needs to
                        // be shortened manually
                        String iri = stripPrefixes(Value.lex(value));
                        singleResult.add(iri);
                    } else {
                        singleResult.add("");
                    }
                }
                queryResult.add(singleResult);
            }
            return queryResult;
        }
    }

    private String stripPrefixes(String string) {
        Map<String, String> prefixMap = new HashMap<String, String>();
        connection
                .namespaces()
                .forEach(namespace -> prefixMap.put(namespace.prefix(), namespace.iri()));
        for (String prefix : prefixMap.keySet()) {
            string = string.replace(prefixMap.get(prefix), prefix + ":");
        }
        return string;
    }

    private void printToConsole(SelectQueryResult results) {
        try {
            QueryResultWriters.write(results, System.out, TextTableQueryResultWriter.FORMAT);
        } catch (IOException e) {
            LOG.error("Failed to write results from query to Console");
        }
    }

    private void selectQuery(String query) {
        SelectQuery stardogSelectQuery = connection.select(query);
        try (SelectQueryResult queryResults = stardogSelectQuery.execute()) {
            System.out.println("The first ten results...");
            printToConsole(queryResults);
        }
    }

    @Override
    public Optional<Result> executeSelectQuery(String query) {
        if (query.length() == 0) {
            // TODO Discuss how to handle errors that would need to be handled in the UI
            return Optional.empty();
        }
        Result res = extractResults(query);
        if (!res.isEmpty()) {
            return Optional.of(res);
        } else {
            return Optional.empty();
        }
    }
}
