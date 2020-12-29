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
		ArchitectureRule rule = new ArchitectureRule();
		rule.setCnlSentence("Only A can use B.");
		rule.setContraintFile("somefile");
		rule.setId(0);
		rule.setType(RuleType.DOMAIN_RANGE);
		
		ConstraintViolationBuilder violationBuilder = new ConstraintViolationBuilder();
		violationBuilder.addViolation("subject", "predicate", "object");
		List<ConstraintViolation> violations = new ArrayList<>();
		violations.add(violationBuilder.build());
		
		CheckedRule r = new CheckedRule(rule, violations);
		
		assertEquals(r.getRule(), rule);
		assertEquals(r.getViolations(), violations);
	}

}
