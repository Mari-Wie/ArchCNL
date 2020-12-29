package conformancecheck.api;

import java.util.ArrayList;
import java.util.List;

import datatypes.ArchitectureRule;
import datatypes.ConstraintViolation;

/**
 * This class stores an architecture rule together with its violations.
 * 
 * Instances are immutable, i.e. value objects.
 */
public class CheckedRule {
	private final ArchitectureRule rule;
	private final List<ConstraintViolation> violations;
	
	/**
	 * Constructor.
	 * @param rule An architecture rule.
	 * @param violations The violations of the rule (may be empty).
	 */
	public CheckedRule(ArchitectureRule rule, List<ConstraintViolation> violations) {
		this.rule = rule;
		this.violations = new ArrayList<>(violations); // copy to prevent modifications
	}

	/**
	 * Returns the rule.
	 */
	public ArchitectureRule getRule() {
		return rule;
	}

	/**
	 * Returns the violations of the rule.
	 */
	public List<ConstraintViolation> getViolations() {
		return new ArrayList<>(violations); // copy to prevent modifications
	}

}
