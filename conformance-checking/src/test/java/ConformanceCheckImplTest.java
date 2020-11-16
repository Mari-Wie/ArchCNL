import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import conformancecheck.api.IConformanceCheck;
import conformancecheck.impl.ConformanceCheckImpl;
import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;
import mockups.StardogDatabaseMockup;
import mockups.StardogICVAPIMockup;

public class ConformanceCheckImplTest {
	
	
	private StardogDatabaseMockup _db;
	private StardogICVAPIMockup _icv;
	private IConformanceCheck _iut; // instance under test
	private final String CONTEXT = "testcontext";
	private ArchitectureRule _rule;
	private final String PATH_TO_CONSTRAINT = "";
	private final int RULE_ID = 0;
	
	@Before
	public void setUp() {
		_db = new StardogDatabaseMockup();
		_icv = new StardogICVAPIMockup();
		_iut = new ConformanceCheckImpl(_icv);
		_rule = new ArchitectureRule();
		_icv.setTestedRuleID(RULE_ID);
		_rule.setId(RULE_ID);
		_rule.setCnlSentence("Every Thing must use Thing.");
	}
	
	@Test
	public void testArchitectureRuleIsValidated() {
		ArchitectureRules.getInstance().addRuleWithPathToConstraint(_rule, RULE_ID, PATH_TO_CONSTRAINT);
		_iut.createNewConformanceCheck();
		_iut.validateRule(_rule, _db, CONTEXT);
		
		assertTrue(_icv.constraintFileHasBeenAddedToServerAndDatabase(PATH_TO_CONSTRAINT, _db.getServer(), _db.getDatabaseName()));
		assertEquals(0, _icv.timesCleared(_db.getServer(), _db.getDatabaseName()));
		
		_iut.storeConformanceCheckingResultInDatabaseForRule(_rule, _db, CONTEXT);

		// TODO: bad temporary approach (type cast), change this once IConformanceCheck has been refactored
		ConformanceCheckImpl temp = (ConformanceCheckImpl) _iut;
		assertTrue(_db.hasStoredFileUnderContext(temp.getResultPath(), CONTEXT));
		
		assertEquals(1, _icv.timesCleared(_db.getServer(), _db.getDatabaseName()));
	}

}
