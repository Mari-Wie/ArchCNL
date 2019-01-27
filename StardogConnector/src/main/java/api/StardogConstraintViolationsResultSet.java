package api;

import java.util.ArrayList;
import java.util.List;

public class StardogConstraintViolationsResultSet {
	
	private int id;
	private List<StardogConstraintViolation> violations;
	
	public StardogConstraintViolationsResultSet(int id) {

		this.id = id;
		violations = new ArrayList<>();
		
	}
	
	public List<StardogConstraintViolation> getViolations() {
		return violations;
	}

	public void addViolation(StardogConstraintViolation violation) {
		violations.add(violation);
	}
	
	public int getId() {
		return id;
	}
	

}
