package conformancecheck.api;

import api.StardogDatabaseInterface;
import api.StardogICVAPI;
import datatypes.ArchitectureRule;

public interface IConformanceCheck {
	
	/**
	 * Sets up a new conformance check.
	 */
	public void createNewConformanceCheck();
	
	// TODO: rewrite comment when dependencies are resolved
	/**
	 * Adds the given architecture rule to the specified database and RDF context as an integrity 
	 * constraint and validates it. {@link #createNewConformanceCheck()} must have been called before.
	 * @param rule The architecture rule to validate. It must be stored in the {@link datatypes.ArchitectureRules} singleton class.
	 * @param db The database to use.
	 * @param context The RDF context to use.
	 */
	public String validateRule(ArchitectureRule rule, StardogDatabaseInterface db, String context, String modelPath, StardogICVAPI icvAPI);
	
	/**
	 * Writes the results of a previous rule validation to an OWL file and removes any integrity constraints
	 * from this database. {@link #validateRule(ArchitectureRule, StardogDatabaseInterface, String)} must have been called before.
	 * @param rule The rule that has been validated.
	 * @param db The database to use.
	 * @param context The RDF context to use.
	 * @param modelPath The path to the OWL file containing the code model from the context in XML format.
	 * @return The path of the OWL file (XML format) to which the results have been written.
	 */
//	public String writeConformanceCheckingResultForRuleToFile(ArchitectureRule rule, StardogDatabaseInterface db, String context, String modelPath);
}
