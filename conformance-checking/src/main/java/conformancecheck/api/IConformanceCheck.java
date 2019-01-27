package conformancecheck.api;

import java.io.FileNotFoundException;

import api.StardogConstraintViolationsResultSet;
import api.exceptions.NoConnectionToStardogServerException;
import datatypes.ArchitectureRule;
import impl.StardogDatabase;

public interface IConformanceCheck {
	
	public void createNewConformanceCheck();
	public void storeArchitectureRule(ArchitectureRule rule);
	public void validateRule(ArchitectureRule rule, StardogDatabase db, String context);
	public void storeConformanceCheckingResultInDatabaseForRule(ArchitectureRule rule, StardogDatabase db, String context);
	public StardogConstraintViolationsResultSet getResult();
//	public void saveResultsToDatabase() throws FileNotFoundException, NoConnectionToStardogServerException;
	public void saveResultsToDatabase(StardogDatabase db, String context) throws FileNotFoundException, NoConnectionToStardogServerException;

}
