package datatypes;

import java.util.ArrayList;
import java.util.List;

public class ConstraintViolationsResultSet {
	
	private List<ConstraintViolation> violations;
	
	public ConstraintViolationsResultSet() {
		violations = new ArrayList<>();
		
	}
	
	public List<ConstraintViolation> getViolations() {
		return violations;
	}

	public void addViolation(ConstraintViolation violation) {
		violations.add(violation);
	}
}
