package datatypes;

import java.util.ArrayList;
import java.util.List;


public class ConstraintViolation {


	private List<StatementTriple> asserted;
	private List<StatementTriple> notInferred;

	public ConstraintViolation() {
		asserted = new ArrayList<>();
		notInferred = new ArrayList<>();
	}

	public void setViolation(String subjectAsserted, String predicateAsserted, String objectAsserted) {
		StatementTriple triple = new StatementTriple(subjectAsserted, predicateAsserted, objectAsserted);
		asserted.add(triple);

	}

	public void setNotInferred(String subjectNotInferred, String predicateNotInferred, String objectNotInferred) {
		StatementTriple triple = new StatementTriple(subjectNotInferred, predicateNotInferred, objectNotInferred);
		notInferred.add(triple);
	}

	public List<StatementTriple> getAsserted() {
		return asserted;
	}
}
