package impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.ConnectionPool;
import com.complexible.stardog.api.ConnectionPoolConfig;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.stardog.stark.Resource;
import com.stardog.stark.Values;
import com.stardog.stark.io.RDFFormats;

import api.StardogDatabaseAPI;
import api.exceptions.MissingBuilderArgumentException;
import api.exceptions.NoConnectionToStardogServerException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
	public void connect() {

        LOG.info("Start connecting ....");
		if (connectionPool == null && connection == null) 
		{
	        LOG.info("No Connection, no pool - start AdminConnection ....");
			try (final AdminConnection aConn = AdminConnectionConfiguration.toServer(_server)
					.credentials(_userName, _password).connect()) 
			{
				if (!aConn.list().contains(_databaseName)) 
				{
					aConn.newDatabase(_databaseName).create();
			        LOG.info("New database created: "+_databaseName);
				}
				else
				{
			        LOG.info("Database already exists: "+_databaseName);
				}
			}

	        LOG.info("Start connection configuration ...");
			ConnectionConfiguration connectionConfig = ConnectionConfiguration.to(_databaseName).server(_server)
				.reasoning(false).credentials(_userName, _password);
			ConnectionPoolConfig poolConfig = ConnectionPoolConfig.using(connectionConfig);
			connectionPool = poolConfig.create();
	        LOG.info("ConnectionPool created.");
			
			connection = connectionPool.obtain();	
	        LOG.info("Connection obtained.");	
		}
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
			Resource resource = Values.iri(context);
			connection.export().context(resource).format(RDFFormats.RDFXML)
					.to(new FileOutputStream(new File(path)));
		} catch (StardogException | FileNotFoundException e) {
			// TODO Auto-generated catch block
	        LOG.error("CONNECTION ERROR: " + e.getClass());
	        LOG.error("CONNECTION ERROR: File not found or no connection to database");
			e.printStackTrace();
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

	public static class StardogDatabaseBuilder {
		private String _server;
		private String _databaseName;

		private String _userName;
		private String _password;

		public StardogDatabaseBuilder server(String server) {
			_server = server;
			return this;
		}

		public StardogDatabaseBuilder databaseName(String name) {
			_databaseName = name;
			return this;
		}

		public StardogDatabaseBuilder userName(String userName) {
			_userName = userName;
			return this;
		}

		public StardogDatabaseBuilder password(String password) {
			_password = password;
			return this;
		}

		public StardogDatabaseAPI createStardogDatabase() throws MissingBuilderArgumentException {
			if (_server != null && _databaseName != null && _userName != null && _password != null) {
				return new StardogDatabase(_server, _databaseName, _userName, _password);
			} else {
		        LOG.error("CONNECTION ERROR: At least one argument was not set.");
				throw new MissingBuilderArgumentException("At least one argument was not set.");
			}
		}

	}

	@Override
	public void addDataByRDFFileAsNamedGraph(String pathToData, String context)
			throws FileNotFoundException, NoConnectionToStardogServerException {
		if (connection == null) {
	        LOG.error("CONNECTION ERROR: No connection.");
			throw new NoConnectionToStardogServerException();
		}

        LOG.info("Adding graph from context ...");
		Resource namedGraph = Values.iri(context);
		connection.begin();
		// declare the transaction
		connection.add().io().context(namedGraph).format(RDFFormats.RDFXML).stream(new FileInputStream(pathToData));
		// and commit the change
		connection.commit();
        LOG.info("Change committed...");
	}
	

}
