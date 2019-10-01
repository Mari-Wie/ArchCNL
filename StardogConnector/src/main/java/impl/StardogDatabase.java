package impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.openrdf.model.Resource;
import org.openrdf.rio.RDFFormat;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.ConnectionPool;
import com.complexible.stardog.api.ConnectionPoolConfig;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;

import api.exceptions.MissingBuilderArgumentException;
import api.exceptions.NoConnectionToStardogServerException;

public class StardogDatabase {

	private String server;
	private String databaseName;
//	private String context;

	private String userName;
	private String password;

	private ConnectionPool connectionPool;
	private Connection connection;

	public StardogDatabase(String server, String databaseName, String userName, String password) {
		this.server = server;
		this.databaseName = databaseName;
//		this.context = context;
		this.userName = userName;
		this.password = password;
	}

	public void connect() {
		if (connectionPool == null && connection == null) {
			try (final AdminConnection aConn = AdminConnectionConfiguration.toServer(server)
					.credentials(userName, password).connect()) {

				if (!aConn.list().contains(this.databaseName)) {
					aConn.disk(this.databaseName).create();
				}
			}

			ConnectionConfiguration connectionConfig = ConnectionConfiguration.to(this.databaseName).server(this.server)
					.reasoning(false).credentials(userName, password);

			ConnectionPoolConfig poolConfig = ConnectionPoolConfig.using(connectionConfig);

			connectionPool = poolConfig.create();
			connection = connectionPool.obtain();
		}
	}

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
			e.printStackTrace();
		}
	}

	public Model getModelFromContext(String context) {
		try {
			connection.export().context(Values.iri(context)).format(RDFFormat.RDFXML)
					.to(new FileOutputStream(new File("./tmp.owl")));
		} catch (StardogException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("File not found or no connection to database"); // TODO Error Handling
		}
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read("./tmp.owl");
		return model;
	}

	public String getServer() {
		return server;
	}

	public String getDatabaseName() {
		return databaseName;
	}

//	public String getContext() {
//		return context;
//	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public static class StardogDatabaseBuilder {
		private String server;
		private String databaseName;

		private String userName;
		private String password;

		public StardogDatabaseBuilder server(String server) {
			this.server = server;
			return this;
		}

		public StardogDatabaseBuilder databaseName(String name) {
			this.databaseName = name;
			return this;
		}

		public StardogDatabaseBuilder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public StardogDatabaseBuilder password(String password) {
			this.password = password;
			return this;
		}

		public StardogDatabase createStardogDatabase() throws MissingBuilderArgumentException {
			if (server != null && databaseName != null && userName != null && password != null) {
				return new StardogDatabase(server, databaseName, userName, password);
			} else {
				throw new MissingBuilderArgumentException("At least one argument was not set.");
			}
		}

	}

	public void addDataByRDFFileAsNamedGraph(String pathToData, String context)
			throws FileNotFoundException, NoConnectionToStardogServerException {
		if (connection == null) {
			throw new NoConnectionToStardogServerException();
		}

		Resource namedGraph = Values.iri(context);
		connection.begin();
		// declare the transaction
		connection.add().io().context(namedGraph).format(RDFFormat.RDFXML).stream(new FileInputStream(pathToData));
		// and commit the change
		connection.commit();
	}
	

}
