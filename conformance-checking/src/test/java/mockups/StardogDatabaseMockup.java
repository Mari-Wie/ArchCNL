package mockups;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import api.StardogDatabaseInterface;
import api.exceptions.NoConnectionToStardogServerException;

public class StardogDatabaseMockup implements StardogDatabaseInterface {

	private boolean _hasConnected = false;
	private boolean _hasDisconnected = false;
	private final String SERVER = "TESTSERVER";
	private final String DATABASE = "TESTDB";
	private final String USER = "TESTUSER";
	private final String PASSWORD = "TESTPASSWORD";
	private Map<String, List<String>> _storedRDFFilesByContext = new HashMap<String, List<String>>();
	
	@Override
	public void connect() {
		_hasConnected = true;
	}

	@Override
	public void closeConnectionToServer() {
		_hasDisconnected = true;
	}

	@Override
	public Model getModelFromContext(String context) {
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		return model;
	}

	@Override
	public String getServer() {
		return SERVER;
	}

	@Override
	public String getDatabaseName() {
		return DATABASE;
	}

	@Override
	public String getUserName() {
		return USER;
	}

	@Override
	public String getPassword() {
		return PASSWORD;
	}

	@Override
	public void addDataByRDFFileAsNamedGraph(String pathToData, String context)
			throws FileNotFoundException, NoConnectionToStardogServerException {
		if (!_storedRDFFilesByContext.containsKey(context)) {
			_storedRDFFilesByContext.put(context, new ArrayList<String>());
		}
		
		_storedRDFFilesByContext.get(context).add(pathToData);
	}

	
	public boolean hasConnected() {
		return _hasConnected;
	}
	
	public boolean hasDisconnected() {
		return _hasDisconnected;
	}
	
	public boolean hasStoredFileUnderContext(String file, String context) {
		return _storedRDFFilesByContext.containsKey(context) && _storedRDFFilesByContext.get(context).contains(file);
	}
}
