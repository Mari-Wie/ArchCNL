package conformancecheck.api;

import java.util.List;

import api.StardogConstraintViolation;
import datatypes.ArchitectureRule;

public interface IConformanceCheck {
	
	public void createNewConformanceCheck();
	public void validateRule(ArchitectureRule rule);
	public void storeConformanceCheckingResult(ArchitectureRule rule, List<StardogConstraintViolation> violations);

}
