package common;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;

public class ArchitectureRulesTest {

	private ArchitectureRules instance;
	private ArchitectureRule r1;
	private ArchitectureRule r2;
	private ArchitectureRule r3;
	
	@Before
	public void setUp() throws Exception {
		instance = ArchitectureRules.getInstance();
		r1 = new ArchitectureRule();
		r1.setCnlSentence("Only toolchain can use a Plugin.");
		r1.setId(0);
		
		r2 = new ArchitectureRule();
		r2.setCnlSentence("No Plugin must use Toolchain.");
		r2.setId(1);
		
		r3 = new ArchitectureRule();
		r3.setCnlSentence("No Plugin must use a Plugin.");
		r3.setId(2);
	}

	@After
    public void teaddown() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// this prevents that the state of the singleton is kept between tests
        resetSingleton();
    }

    public static void resetSingleton() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	Field instance = ArchitectureRules.class.getDeclaredField("instance");
    	instance.setAccessible(true);
    	instance.set(null, null);
    }
	
	@Test
	public void testGetRuleWithID() {
		
		instance.addRuleWithPathToConstraint(r1, 0, "bla");
		instance.addRuleWithPathToConstraint(r2, 1, "blub");
		
		assertEquals(instance.getRuleWithID(0), r1);
		assertEquals(instance.getRuleWithID(1), r2);
		assertNull(instance.getRuleWithID(3));
	}

	@Test 
	public void testConstraintStorage() {
		final String path = "somepath";
		
		assertNull(instance.getPathOfConstraintForRule(r1));
		assertNull(instance.getPathOfConstraintForRule(r2));
		
		instance.addRuleWithPathToConstraint(r1, 0, path);
		
		assertEquals(instance.getPathOfConstraintForRule(r1), path);
		assertNull(instance.getPathOfConstraintForRule(r2));
	}
	
	@Test
	public void testGetRules() {

		assertFalse(instance.getRules().contains(r1));
		assertFalse(instance.getRules().contains(r2));
		assertEquals(instance.getRules().size(), 0);
		
		instance.addRuleWithPathToConstraint(r1, 0, "");
		
		assertTrue(instance.getRules().contains(r1));
		assertFalse(instance.getRules().contains(r2));
		assertEquals(instance.getRules().size(), 1);
		
		instance.addRuleWithPathToConstraint(r2, 1, "");
		
		assertTrue(instance.getRules().contains(r1));
		assertTrue(instance.getRules().contains(r2));
		assertEquals(instance.getRules().size(), 2);
	}
	
	@Test
	public void testThatWeirdBugDoesNotHappenAgain() {
		// The weird bug was that the architecture rules have been
		// modified after being inserted into the singleton's
		// hash map. Java's hash map has unpredictable behavior
		// in this case and it broke the contains() and get() methods.
		instance.addRuleWithPathToConstraint(r1, 0, "");
		
		r1.setCnlSentence("No Bug must exist-in Project");

		assertEquals(instance.getRuleWithID(0), r1);
		
		r1.setContraintFile("another file");

		assertEquals(instance.getRuleWithID(0), r1);
	}
}
