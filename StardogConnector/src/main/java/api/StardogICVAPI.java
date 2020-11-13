package api;

import java.io.FileNotFoundException;
import java.util.Map;
//import org.apache.jena.rdf.model.Model;
//import com.complexible.stardog.StardogException;

import api.exceptions.ConstraintsNotAddedException;
import api.exceptions.StardogDatabaseDoesNotExist;

public interface StardogICVAPI {
	
	//public void validateIntegrityConstraints(String pathToConstraints, StardogConnectionAPI api) throws StardogDatabaseDoesNotExist, FileNotFoundException, ConstraintsNotAddedException;

//	/**
//	 * Connects to the given database and adds the integrity constraints stored in the specified file to the database.
//	 * @param pathToConstraints The path to the RDF-file in XML format which stores the integrity constraints to add.
//	 * @param server The name of the database server to connect to.
//	 * @param database The name of the database to connect to.
//	 * @throws FileNotFoundException When the input file cannot be accessed.
//	 */
//	public void addIntegrityConstraints(String pathToConstraints, String server, String database) throws FileNotFoundException;
	
//	/**
//	 * Explains integrity constraint violations. Integrity constraints must be imported and checked before calling this operation, e.g. by calling the operation {@link StardogICVAPI#validateIntegrityConstraints(String, StardogConnectionAPI)}
//	 * @param server
//	 * @param database
//	 */
//	public void explainViolations(String server, String database);
	
	public Map<String, StardogConstraintViolationResult> getViolations();

	void validateIntegrityConstraintsInContext(String pathToConstraints, String server, String database, String context)
			throws FileNotFoundException;

	public void explainViolationsForContext(String server, String database, String context);

	public String addIntegrityConstraint(int id, String pathToConstraint, String server, String database)
			throws FileNotFoundException;

	Map<Integer, String> getConstraintsAsString();

	void removeIntegrityConstraints(String pathToConstraints, String server, String database) throws FileNotFoundException;

	public StardogConstraintViolationsResultSet getResult();



}
