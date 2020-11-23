package datatypes;

import java.util.ArrayList;
import java.util.List;

public class ConstraintViolationsResultSet {
	
//	private int id;
	private List<ConstraintViolation> violations;
	
	public ConstraintViolationsResultSet(int id) {

//		this.id = id;
		violations = new ArrayList<>();
		
	}
	
	public List<ConstraintViolation> getViolations() {
		return violations;
	}

	public void addViolation(ConstraintViolation violation) {
		violations.add(violation);
	}
//	
//	public int getId() {
//		return id;
//	}
//	

}
