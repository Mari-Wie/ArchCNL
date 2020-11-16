import static org.junit.Assert.fail;

import org.junit.Test;

import api.StardogDatabaseInterface;
import api.StardogICVAPI;
import conformancecheck.api.IConformanceCheck;
import conformancecheck.impl.ConformanceCheckImpl;
import datatypes.ArchitectureRule;
import mockups.StardogDatabaseMockup;
import mockups.StardogICVAPIMockup;

public class ConformanceCheckImplTest {
	
	
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
