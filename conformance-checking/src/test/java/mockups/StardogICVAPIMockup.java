package mockups;

import java.io.FileNotFoundException;
import java.util.Map;

import api.StardogConnectionAPI;
import api.StardogConstraintViolationResult;
import api.StardogConstraintViolationsResultSet;
import api.StardogICVAPI;
import api.exceptions.ConstraintsNotAddedException;
import api.exceptions.StardogDatabaseDoesNotExist;

public class StardogICVAPIMockup implements StardogICVAPI {

//	@Override
//	public void validateIntegrityConstraints(String pathToConstraints, StardogConnectionAPI api)
//			throws StardogDatabaseDoesNotExist, FileNotFoundException, ConstraintsNotAddedException {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public void addIntegrityConstraints(String pathToConstraints, String server, String database)
			throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void explainViolations(String server, String database) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, StardogConstraintViolationResult> getViolations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateIntegrityConstraintsInContext(String pathToConstraints, String server, String database,
			String context) throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void explainViolationsForContext(String server, String database, String context) {
		// TODO Auto-generated method stub

	}

	@Override
	public String addIntegrityConstraint(int id, String pathToConstraint, String server, String database)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, String> getConstraintsAsString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeIntegrityConstraints(String pathToConstraints, String server, String database)
			throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public StardogConstraintViolationsResultSet getResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
