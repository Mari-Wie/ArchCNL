import static org.junit.Assert.fail;

import org.junit.Test;

//import api.StardogConnectionAPI;
//import api.StardogICVAPI;
//import conformancecheck.api.IConformanceCheck;
//import conformancecheck.impl.ConformanceCheckImpl;
//import conformancecheck.impl.ConformanceCheckOntology;
import datatypes.ArchitectureRule;

public class ConformanceCheckImplTest {
	
	//	private StardogConnectionAPI connectionAPI;
	//private StardogICVAPI icvAPI;
	//private IConformanceCheck check;
	//private ConformanceCheckOntology ontology;
	
	@Test
	public void testCreateNewConformanceCheckOntology() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testArchitectureRuleIsStoredInDatabase() {
		ArchitectureRule rule = new ArchitectureRule();
		rule.setCnlSentence("Every LogicType must use a DBType");
		
		//check = new ConformanceCheckImpl(connectionAPI, icvAPI, "");
		
	}
	
	@Test
	public void testArchitectureRuleIsValidated() {
		
	}

}
