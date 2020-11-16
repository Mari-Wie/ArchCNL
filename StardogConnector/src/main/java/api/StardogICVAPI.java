package api;

import java.io.FileNotFoundException;

public interface StardogICVAPI {

	/**
	 * Connects to the given database and checks whether each of the 
	 * current integrity constraints is violated in the given context. 
	 * The explanations/proofs of identified violations can be queried with 
	 * {@link #getResult()}.
	 * @param server The name of the database server to connect to.
	 * @param database The name of the database to connect to.
	 * @param context The URI of the RDF context to use.
	 */
	public void explainViolationsForContext(String server, String database, String context);

	/**
	 * Connects to the given database and adds the integrity constraints stored in the specified file to the database.
	 * @param pathToConstraints The path to the RDF-file in XML format which stores the integrity constraints to add.
	 * @param server The name of the database server to connect to.
	 * @param database The name of the database to connect to.
	 * @return A string representation of the added constraint.
	 * @throws FileNotFoundException When the input file cannot be accessed.
	 */
	public String addIntegrityConstraint(String pathToConstraint, String server, String database)
			throws FileNotFoundException;

	/**
	 * Connects to the given database and removes all integrity constraints from it.
	 * @param server The name of the database server to connect to.
	 * @param database The name of the database to connect to.
	 */
	void removeIntegrityConstraints(String server, String database);

	/**
	 * Returns the violations of integrity contraints as well as the explanetions/proofs
	 * of such violations which have been identified by a previous call of
	 * {@link #explainViolationsForContext(String, String, String)}.
	 * @return the violations and their explanations
	 */
	public StardogConstraintViolationsResultSet getResult();



}
