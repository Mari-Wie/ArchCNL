import static org.junit.Assert.fail;

import org.junit.Test;

import api.StardogDatabaseInterface;
import api.StardogICVAPI;
import conformancecheck.api.IConformanceCheck;
import conformancecheck.impl.ConformanceCheckImpl;
//import api.StardogConnectionAPI;
//import api.StardogICVAPI;
//import conformancecheck.api.IConformanceCheck;
//import conformancecheck.impl.ConformanceCheckImpl;
//import conformancecheck.impl.ConformanceCheckOntology;
import datatypes.ArchitectureRule;
import mockups.StardogDatabaseMockup;
import mockups.StardogICVAPIMockup;

public class ConformanceCheckImplTest {
	
	//	private StardogConnectionAPI connectionAPI;
	//private StardogICVAPI icvAPI;
	//private IConformanceCheck check;
	//private ConformanceCheckOntology ontology;
	
	private StardogDatabaseInterface _db;
	private StardogICVAPI _icv;
	private IConformanceCheck _iut; // instance under test
	
	public void setUp() {
		_db = new StardogDatabaseMockup();
		_icv = new StardogICVAPIMockup();
		_iut = new ConformanceCheckImpl(_icv);
	}
	
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
