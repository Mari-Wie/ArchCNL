package api;

import java.io.FileNotFoundException;

import datatypes.ConstraintViolationsResultSet;

public interface StardogICVAPI {

	/**
	 * Connects to the given database and checks whether each of the 
	 * current integrity constraints is violated in the given context. 
	 * The explanations/proofs of identified violations can be queried with 
	 * {@link #getResult()}.
	 * @param context The URI of the RDF context to use.
	 */
	public void explainViolationsForContext(String context);

	/**
	 * Connects to the given database and adds the integrity constraints stored in the specified file to the database.
	 * @param pathToConstraints The path to the RDF-file in XML format which stores the integrity constraints to add.
	 * @return A string representation of the added constraint.
	 * @throws FileNotFoundException When the input file cannot be accessed.
	 */
	public String addIntegrityConstraint(String pathToConstraint)
			throws FileNotFoundException;

	/**
	 * Connects to the given database and removes all integrity constraints from it.
	 */
	void removeIntegrityConstraints();

	/**
	 * Returns the violations of integrity contraints as well as the explanetions/proofs
	 * of such violations which have been identified by a previous call of
	 * {@link #explainViolationsForContext(String, String, String)}.
	 * @return the violations and their explanations
	 */
	public ConstraintViolationsResultSet getResult();
}
