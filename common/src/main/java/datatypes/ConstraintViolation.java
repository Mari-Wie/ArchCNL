package datatypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a violation of an architecture rule in OWL syntax.
 */
public class ConstraintViolation {
	private List<StatementTriple> asserted;
	private List<StatementTriple> notInferred;

	/**
	 * Constructor.
	 */
	public ConstraintViolation() {
		asserted = new ArrayList<>();
		notInferred = new ArrayList<>();
	}

	/**
	 * Add an asserted OWL statement (subject predicate object) to this violation.
	 * @param subjectAsserted the URI of the subject
	 * @param predicateAsserted the URI of the predicate
	 * @param objectAsserted the URI of the object
	 */
	public void setViolation(String subjectAsserted, String predicateAsserted, String objectAsserted) {
		StatementTriple triple = new StatementTriple(subjectAsserted, predicateAsserted, objectAsserted);
		asserted.add(triple);
	}

	/**
	 * Add a OWL statement (subject predicate object) which could not be inferred to this violation.
	 * @param subjectAsserted the URI of the subject
	 * @param predicateAsserted the URI of the predicate
	 * @param objectAsserted the URI of the object
	 */
	public void setNotInferred(String subjectNotInferred, String predicateNotInferred, String objectNotInferred) {
		StatementTriple triple = new StatementTriple(subjectNotInferred, predicateNotInferred, objectNotInferred);
		notInferred.add(triple);
	}

	/**
	 * Returns the list of asserted statements. 
	 */
	public List<StatementTriple> getAsserted() {
		return new ArrayList<>(asserted);
	}
}
