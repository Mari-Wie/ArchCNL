package api;

import java.util.ArrayList;
import java.util.List;

import impl.StardogConstraint;

public class StardogConstraintViolationResult {
	
	private final StardogConstraint constraint;
	private List<StardogConstraintViolation> constraintViolations;
	
	public StardogConstraintViolationResult(StardogConstraint constraint) {
		this.constraint = constraint;
		constraintViolations = new ArrayList<>();
	}
	
	public void addViolation(StardogConstraintViolation violation) {
		constraintViolations.add(violation);
	}
	
	public List<StardogConstraintViolation> getViolations() {
		return constraintViolations;
	}
	
	public String getConstraintAsString() {
		return constraint.getConstraint().toString();
	}
	
	public StardogConstraint getConstraint() {
		return constraint;
	}

}
