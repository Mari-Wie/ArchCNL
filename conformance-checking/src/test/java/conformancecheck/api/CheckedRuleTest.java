package conformancecheck.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import conformancecheck.api.CheckedRule;
import datatypes.ArchitectureRule;
import datatypes.ConstraintViolation;
import datatypes.RuleType;
import datatypes.ConstraintViolation.ConstraintViolationBuilder;

public class CheckedRuleTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		ArchitectureRule rule = new ArchitectureRule(0, "Only A can use B.", RuleType.DOMAIN_RANGE, "some file");
		
		ConstraintViolationBuilder violationBuilder = new ConstraintViolationBuilder();
		violationBuilder.addViolation("subject", "predicate", "object");
		List<ConstraintViolation> violations = new ArrayList<>();
		violations.add(violationBuilder.build());
		
		CheckedRule r = new CheckedRule(rule, violations);
		
		assertEquals(r.getRule(), rule);
		assertEquals(r.getViolations(), violations);
	}

}
