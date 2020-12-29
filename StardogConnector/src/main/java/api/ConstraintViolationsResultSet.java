package api;

import java.util.ArrayList;
import java.util.List;

import datatypes.ConstraintViolation;

/**
 * Stores violations for OWL integrity constraints.
 */
public class ConstraintViolationsResultSet {
	
	private List<ConstraintViolation> violations;
	
	/**
	 * Constructor.
	 */
	public ConstraintViolationsResultSet() {
		violations = new ArrayList<>();
		
	}
	
	/**
	 * Returns a list of all violations in this set.
	 */
	public List<ConstraintViolation> getViolationList() {
		return violations;
	}

	/**
	 * Adds a violation to this set.
	 * @param violation The violation to add.
	 */
	public void addViolation(ConstraintViolation violation) {
		violations.add(violation);
	}
}
