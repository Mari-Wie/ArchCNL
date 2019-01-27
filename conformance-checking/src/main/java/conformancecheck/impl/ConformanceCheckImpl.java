package conformancecheck.impl;

import java.util.List;

import com.google.inject.Inject;

import api.StardogConnectionAPI;
import api.StardogConstraintViolation;
import api.StardogICVAPI;
import conformancecheck.api.IConformanceCheck;
import datatypes.ArchitectureRule;

public class ConformanceCheckImpl implements IConformanceCheck {
	
	/*TODO: Dependencies to Stardog: ICV and Connection*/
	private StardogConnectionAPI connectionAPI;
	private StardogICVAPI icvAPI;
	
	private ConformanceCheckOntology ontology;
	
	@Inject
	public ConformanceCheckImpl(StardogConnectionAPI connectionAPI, StardogICVAPI icvAPI) {
		this.connectionAPI = connectionAPI;
		this.icvAPI = icvAPI;
	}
	

	public void createNewConformanceCheck() {
		//TODO: initializes the conformance ontology
		
	}

	public void validateRule(ArchitectureRule rule) {
		// TODO Auto-generated method stub
		
	}

	public void storeConformanceCheckingResult(ArchitectureRule rule, List<StardogConstraintViolation> violations) {
		// TODO connects the code model with conformance check instances
		
	}

}
