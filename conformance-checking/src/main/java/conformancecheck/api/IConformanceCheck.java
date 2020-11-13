package conformancecheck.api;

import java.io.FileNotFoundException;

import api.StardogConstraintViolationsResultSet;
import api.StardogDatabaseInterface;
import api.exceptions.NoConnectionToStardogServerException;
import datatypes.ArchitectureRule;

public interface IConformanceCheck {
	
	public void createNewConformanceCheck();
	public void storeArchitectureRule(ArchitectureRule rule);
	public void validateRule(ArchitectureRule rule, StardogDatabaseInterface db, String context);
	public void storeConformanceCheckingResultInDatabaseForRule(ArchitectureRule rule, StardogDatabaseInterface db, String context);
	public StardogConstraintViolationsResultSet getResult();
//	public void saveResultsToDatabase() throws FileNotFoundException, NoConnectionToStardogServerException;
	public void saveResultsToDatabase(StardogDatabaseInterface db, String context) throws FileNotFoundException, NoConnectionToStardogServerException;

}
