package api;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.jena.rdf.model.Model;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResultHandlerException;
import org.openrdf.query.resultio.UnsupportedQueryResultFormatException;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;

import api.exceptions.NoConnectionToStardogServerException;
import impl.QueryResultSet;
import impl.StardogDatabase;

public interface StardogConnectionAPI {
	
	/**
	 * Gets an instance of the API. StardogConnectionAPI is a singleton.
	 * 
	 * @return
	 */
	public StardogConnectionAPI getInstance();

	/**
	 * Establishes a connection to a Stardog server using user credentials specified
	 * by userName and password. If the database does not exist, the database is created.
	 * 
	 * @param stardogServerURL
	 * @param userName
	 * @param password
	 */
	public void createConnectionToDatabase(String stardogServerURL, String userName, String password, String database);
	
	/**
	 * Closes the connection to the server. 
	 * 
	 * @param stardogServerURL
	 */
	public void closeConnectionToServer(String stardogServerURL);

	/**
	 * Adds data from a file (RDF format) to the database on the server
	 * for which a connection currently exists. If there is no connection to any
	 * server, an exception is thrown.
	 * 
	 * @param pathToData
	 * @param database
	 * @throws FileNotFoundException 
	 * @throws StardogException 
	 * @throws NoConnectionToStardogServerException
	 */
	public void addDataByRDFFile(String pathToData) throws NoConnectionToStardogServerException, FileNotFoundException;
	
	public Connection getConnection();
	
	public QueryResultSet executeSelectQuery(String query);

	void addDataByRDFFileAsNamedGraph(String pathToData, String namedGraph)
			throws FileNotFoundException, NoConnectionToStardogServerException;

	public void storeModelInContext(Model model);

	public Model getModelFromContext(String context);

	public void createConnectionToDatabase(StardogDatabase db);
}
