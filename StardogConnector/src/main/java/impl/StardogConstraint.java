package impl;

import com.complexible.stardog.icv.Constraint;

public class StardogConstraint {
	
	private int id;
	private Constraint constraint;

	public StardogConstraint(int id, Constraint constraint) {
		this.id = id;
		this.constraint = constraint;
	}
	
	public int getId() {
		return id;
	}
	
	public Constraint getConstraint() {
		return constraint;
	}

}
