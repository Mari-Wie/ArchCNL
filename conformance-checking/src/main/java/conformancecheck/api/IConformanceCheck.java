package conformancecheck.api;

import java.io.FileNotFoundException;

import api.StardogConstraintViolationsResultSet;
import api.StardogDatabaseInterface;
import api.exceptions.NoConnectionToStardogServerException;
import datatypes.ArchitectureRule;

public interface IConformanceCheck {
	
	/**
	 * Sets up a new conformance check.
	 */
	public void createNewConformanceCheck();
	
//	/**
//	 * Adds a new architecture rule to be checked. 
//	 * {@link #createNewConformanceCheck()} must have been called before.
//	 * @param rule The rule to store.
//	 */
//	public void storeArchitectureRule(ArchitectureRule rule);
	
	/**
	 * Adds the given architecture rule to the specified database and RDF context as an integrity 
	 * constraint and validates it. {@link #createNewConformanceCheck()} must have been called before.
	 * @param rule The architecture rule to validate. It must be stored in the {@link datatypes.ArchitectureRules} singleton class.
	 * @param db The database to use.
	 * @param context The RDF context to use.
	 */
	public void validateRule(ArchitectureRule rule, StardogDatabaseInterface db, String context);
	
	/**
	 * Stores the results of a previous rule validation in the given database and removes any integrity contraints
	 * from this database. {@link #validateRule(ArchitectureRule, StardogDatabaseInterface, String)} must have been called before.
	 * @param rule The rule that has been validated.
	 * @param db The database to use.
	 * @param context The RDF context to use.
	 */
	public void storeConformanceCheckingResultInDatabaseForRule(ArchitectureRule rule, StardogDatabaseInterface db, String context);
	
//	/**
//	 * 
//	 * @param db
//	 * @param context
//	 * @throws FileNotFoundException
//	 * @throws NoConnectionToStardogServerException
//	 */
//	public void saveResultsToDatabase(StardogDatabaseInterface db, String context) throws FileNotFoundException, NoConnectionToStardogServerException;

}
