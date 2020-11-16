package reasoners;

import java.io.FileNotFoundException;

public interface IntegrityConstraintChecker {
	/**
	 * Checks whether each of the previously added
	 * integrity constraints is violated. 
	 * @return the identified violations and their explanations
	 */
	public ConstraintViolationsResultSet explainViolationsForContext();

	/**
	 * Adds the integrity constraints stored in the specified file.
	 * @param pathToConstraints The path to the RDF-file in XML format which stores the integrity constraints to add.
	 * @return A string representation of the added constraint.
	 * @throws FileNotFoundException When the input file cannot be accessed.
	 */
	public String addIntegrityConstraint(String pathToConstraint)
			throws FileNotFoundException;

	/**
	 * Removes all integrity constraints.
	 */
	void removeIntegrityConstraints();
}
