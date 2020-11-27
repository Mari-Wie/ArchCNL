package conformancecheck.api;

import java.util.Map;

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
	 * is specified by a parameter.
	 * {@link #createNewConformanceCheck()} must have been called before
	 * this method is called.
	 * @param rule The architecture rule to validate. It must be stored in the {@link datatypes.ArchitectureRules} singleton class.
	 * @param modelPath The path to the RDF/OWL file (XML format) containing the code model to use.
	 * @param violations Set of architecture violations to add.
	 * @param outputPath The path to the file to which the results will be written.
	 */
	public void validateRule(ArchitectureRule rule, String modelPath, ConstraintViolationsResultSet violations, String outputPath);
	
	/**
	 * @return 
	 * 	Returns a map which contains all OWL namespaces which are provided by the component.
	 * 	The keys are the abbreviations, and the values are the full URIs of these namespaces.
	 */
	public Map<String, String> getProvidedNamespaces();
}
