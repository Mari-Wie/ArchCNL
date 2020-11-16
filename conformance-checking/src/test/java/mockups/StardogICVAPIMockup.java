package mockups;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.StardogConstraintViolationsResultSet;
import api.StardogICVAPI;

public class StardogICVAPIMockup implements StardogICVAPI {

	private Map<String, Map<String, List<String>>> _constraintFilesByDatabaseAndServer = new HashMap<String, Map<String, List<String>>>();
	private Map<String, Map<String, Integer>> _timesClearedByDatabaseAndServer = new HashMap<String, Map<String, Integer>>();

	@Override
	public void explainViolationsForContext(String server, String database, String context) {
		// TODO Auto-generated method stub

	}

	@Override
	public String addIntegrityConstraint(String pathToConstraint, String server, String database)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		return "foobar";
	}

	@Override
	public void removeIntegrityConstraints(String server, String database) {
		// TODO Auto-generated method stub
		if (!_timesClearedByDatabaseAndServer.containsKey(server)) {
			_timesClearedByDatabaseAndServer.put(server, new HashMap<String, Integer>());
		}
		if (!_timesClearedByDatabaseAndServer.get(server).containsKey(database)) {
			_timesClearedByDatabaseAndServer.get(server).put(database, 0);
		}
		_timesClearedByDatabaseAndServer
			.get(server)
			.put(database, _timesClearedByDatabaseAndServer
					.get(server)
					.get(database) + 1);

	}

	@Override
	public StardogConstraintViolationsResultSet getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int timesCleared(String server, String database) {
		if (!_timesClearedByDatabaseAndServer.containsKey(server) || 
				!_timesClearedByDatabaseAndServer.get(server).containsKey(database)) {
			return 0;
		}
		return _timesClearedByDatabaseAndServer.get(server).get(database);
	}
	
	public boolean constraintFileHasBeenAddedToServerAndDatabase(String pathToConstraintFile, String server, String database) {
		return _constraintFilesByDatabaseAndServer.containsKey(server) && 
				_constraintFilesByDatabaseAndServer.get(server).containsKey(database) &&
				_constraintFilesByDatabaseAndServer.get(server).get(database).contains(pathToConstraintFile);
	}
}
