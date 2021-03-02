package org.archcnl.stardogwrapper.impl;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.exceptions.MissingBuilderArgumentException;
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
	public void connect() {

        LOG.info("Connecting to the database ....");
		if (connectionPool == null && connection == null) 
		{
	        LOG.debug("No Connection, no pool - creating new ones ....");
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
			        LOG.warn("Database already exists: "+_databaseName);
				}
			}

	        LOG.debug("Start connection configuration ...");
			ConnectionConfiguration connectionConfig = ConnectionConfiguration.to(_databaseName).server(_server)
				.reasoning(false).credentials(_userName, _password);
			ConnectionPoolConfig poolConfig = ConnectionPoolConfig.using(connectionConfig);
			connectionPool = poolConfig.create();
	        LOG.debug("ConnectionPool created.");
			
			connection = connectionPool.obtain();	
	        LOG.debug("Connection obtained.");	
		}
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

        LOG.debug("Adding ontology from file: " + pathToData);
		Resource namedGraph = Values.iri(context);
		connection.begin();
		// declare the transaction
		connection.add().io().context(namedGraph).format(RDFFormats.RDFXML).stream(new FileInputStream(pathToData));
		// and commit the change
		connection.commit();
        LOG.info("Change committed.");
	}
}
