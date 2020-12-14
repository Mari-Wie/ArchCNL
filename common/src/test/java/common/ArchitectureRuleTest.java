package common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datatypes.ArchitectureRule;
import datatypes.RuleType;

public class ArchitectureRuleTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEqualsAndHashCode() {
		ArchitectureRule r1 = new ArchitectureRule();
		ArchitectureRule r2 = new ArchitectureRule();
		ArchitectureRule r3 = new ArchitectureRule();
		
		r1.setCnlSentence("test");
		r2.setCnlSentence("test");
		r3.setCnlSentence("not test");
		
		r1.setId(0);
		r2.setId(0);
		r3.setId(1);
		
		r1.setType(RuleType.AT_LEAST);
		r2.setType(RuleType.AT_LEAST);
		r3.setType(RuleType.AT_MOST);
		
		assertEquals(r1, r1);
		assertEquals(r1, r2);
		assertNotEquals(r1, null);
		assertNotEquals(r1, r3);
		
		assertEquals(r1.hashCode(), r2.hashCode());
	}

}
