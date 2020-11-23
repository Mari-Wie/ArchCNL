package conformancecheck.api;

import datatypes.ArchitectureRule;
import datatypes.ConstraintViolationsResultSet;

public interface IConformanceCheck {
	
	/**
	 * Sets up a new conformance check.
	 */
	public void createNewConformanceCheck();
	
	/**
	 * Loads the given OWL ontology file containing a code model and adds 
	 * the architecture violations for the given architecture rule to it.
	 * The resulting model is stored in a new file. The path of this file 
	 * is returned by this method.
	 * {@link #createNewConformanceCheck()} must have been called before
	 * this method is called.
	 * @param rule The architecture rule to validate. It must be stored in the {@link datatypes.ArchitectureRules} singleton class.
	 * @param modelPath The path to the RDF/OWL file (XML format) containing the code model to use.
	 * @param violations Set of architecture violations to add.
	 */
	public String validateRule(ArchitectureRule rule, String modelPath, ConstraintViolationsResultSet violations);
}
