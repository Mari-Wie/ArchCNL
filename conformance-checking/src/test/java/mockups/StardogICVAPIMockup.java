package mockups;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.StardogConnectionAPI;
import api.StardogConstraintViolationResult;
import api.StardogConstraintViolationsResultSet;
import api.StardogICVAPI;
import api.exceptions.ConstraintsNotAddedException;
import api.exceptions.StardogDatabaseDoesNotExist;

public class StardogICVAPIMockup implements StardogICVAPI {

	private Map<String, Map<String, List<String>>> _constraintFilesByDatabaseAndServer = new HashMap<String, Map<String, List<String>>>();

	@Override
	public void explainViolationsForContext(String server, String database, String context) {
		// TODO Auto-generated method stub

	}

	@Override
	public String addIntegrityConstraint(String pathToConstraint, String server, String database)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeIntegrityConstraints(String server, String database) {
		// TODO Auto-generated method stub

	}

	@Override
	public StardogConstraintViolationsResultSet getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean constraintFileHasBeenAddedToServerAndDatabase(String pathToConstraintFile, String server, String database) {
		return _constraintFilesByDatabaseAndServer.containsKey(server) && 
				_constraintFilesByDatabaseAndServer.get(server).containsKey(database) &&
				_constraintFilesByDatabaseAndServer.get(server).get(database).contains(pathToConstraintFile);
	}
}
