package impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.openrdf.query.TupleQueryResult;

import org.openrdf.model.Resource;
import org.openrdf.query.Binding;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;
//import org.openrdf.rio.RDFFormat;
//import com.complexible.common.rdf.model.Values;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.ConnectionPool;
import com.complexible.stardog.api.ConnectionPoolConfig;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;

import api.StardogConnectionAPI;
import api.StardogDatabaseInterface;
import api.exceptions.NoConnectionToStardogServerException;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.SelectQueryResult;

import com.complexible.common.openrdf.query.ForwardingTupleQueryResult;


public class StardogConnectionAPIImpl implements StardogConnectionAPI {

	private String server;
	private String userName;
	private String password;
	private String databaseName;

	private StardogConnectionAPI instance;

	private ConnectionPool connectionPool;
	private Connection connection;

	@Override
	public void addDataByRDFFile(String pathToData) throws FileNotFoundException, NoConnectionToStardogServerException {

		if (connection == null) {
			throw new NoConnectionToStardogServerException();
		}

		connection.begin();
		// declare the transaction
		connection.add().io().format(RDFFormats.RDFXML).stream(new FileInputStream(pathToData));
		// and commit the change
		connection.commit();
	}
	
	@Override
	public void addDataByRDFFileAsNamedGraph(String pathToData, String namedGraph) throws FileNotFoundException, NoConnectionToStardogServerException {

		if (connection == null) { 
			throw new NoConnectionToStardogServerException();
		}
		
		IRI context = Values.iri(namedGraph);
		connection.begin();
		// declare the transaction
		connection.add().io().context(context).format(RDFFormats.RDFXML).stream(new FileInputStream(pathToData));
		// and commit the change
		connection.commit();
	}
	
	@Override
	public void closeConnectionToServer(String stardogServerURL) {

		releaseConnection();
		connectionPool.shutdown();

		connectionPool = null;
		connection = null;

	}

	@Override
	public void createConnectionToDatabase(String stardogServerURL, String userName, String password, String database) {

		this.userName = userName;
		this.password = password;
		this.databaseName = database;
		this.server = stardogServerURL;

		if (connectionPool == null && connection == null) {
			try (final AdminConnection aConn = AdminConnectionConfiguration.toServer(server).credentials(userName, password)
					.connect()) {

				if (!aConn.list().contains(this.databaseName)) {
					aConn.newDatabase(this.databaseName).create();
				}
			}

			ConnectionConfiguration connectionConfig = ConnectionConfiguration.to(this.databaseName)
					.server(stardogServerURL).reasoning(false).credentials(userName, password);

			ConnectionPoolConfig poolConfig = ConnectionPoolConfig.using(connectionConfig);

			connectionPool = poolConfig.create();
			connection = connectionPool.obtain();

		}
	}
	
	@Override
	public void createConnectionToDatabase(StardogDatabaseInterface db) {
		this.userName = db.getUserName();
		this.password = db.getPassword();
		this.databaseName = db.getDatabaseName();
		this.server = db.getServer();

		if (connectionPool == null && connection == null) {
			try (final AdminConnection aConn = AdminConnectionConfiguration.toServer(server).credentials(userName, password)
					.connect()) {

				if (!aConn.list().contains(this.databaseName)) {
					aConn.newDatabase(this.databaseName).create();
				}
			}

			ConnectionConfiguration connectionConfig = ConnectionConfiguration.to(this.databaseName)
					.server(this.server).reasoning(false).credentials(userName, password);

			ConnectionPoolConfig poolConfig = ConnectionPoolConfig.using(connectionConfig);

			connectionPool = poolConfig.create();
			connection = connectionPool.obtain();

		}
	}

	private void releaseConnection() {
		try {
			connectionPool.release(connection);
		} catch (StardogException e) {
			e.printStackTrace();
		}
	}

	@Override
	public StardogConnectionAPI getInstance() {
		if (instance == null) {
			instance = new StardogConnectionAPIImpl();
		}
		return instance;
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public QueryResultSet executeSelectQuery(String query) {

		SelectQuery selectQuery = connection.select(query);
		selectQuery.limit(SelectQuery.NO_LIMIT);
		TupleQueryResult tupleQueryResult = (TupleQueryResult) selectQuery.execute();
		//SelectQueryResult selectQueryResult = selectQuery.execute();

		QueryResultSet resultSet = new QueryResultSet();

		while (tupleQueryResult.hasNext()) {
			org.openrdf.query.BindingSet next = tupleQueryResult.next();
			QueryResult result = new QueryResult();
			
			for (String bindingName : tupleQueryResult.getBindingNames()) {
				Binding binding = next.getBinding(bindingName);
				result.addResultTuple(bindingName, binding.getValue().stringValue());
			}
			
			resultSet.addResult(result);
		}
		
		// *Always* close your result sets, they hold resources which need to be
		// released.
		tupleQueryResult.close();
		
		return resultSet;
	}

	@Override
	public void storeModelInContext(Model model) {
		
	}
	
	public Model getModelFromContext(String context) {
		try {
			connection.export().context(Values.iri(context)).format(RDFFormats.RDFXML).to(new FileOutputStream(new File("./tmp.owl")));
		} catch (StardogException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("File not found or no connection to database"); //TODO Error Handling
		}
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read("./tmp.owl");
		return model;
	}

	

}
