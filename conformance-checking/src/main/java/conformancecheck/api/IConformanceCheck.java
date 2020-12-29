package conformancecheck.api;

import java.io.FileNotFoundException;
import java.util.Map;

public interface IConformanceCheck {
	
	/**
	 * Sets up a new conformance check.
	 */
	public void createNewConformanceCheck();
	
	/**
	 * Loads the given OWL ontology file containing a code model and adds 
	 * the given architecture rule with its violations to it.
	 * The resulting model is stored in a new file. The path of this file 
	 * is specified by a parameter.
	 * {@link #createNewConformanceCheck()} must have been called before
	 * this method is called.
	 * @param rule The violated architecture rule and its violations.
	 * @param modelPath The path to the RDF/OWL file (XML format) containing the code model to use.
	 * @param outputPath The path to the file to which the results will be written.
	 * @throws FileNotFoundException when the input file cannot be read
	 */
	public void validateRule(CheckedRule rule, String modelPath, String outputPath) throws FileNotFoundException;
	
	/**
	 * @return 
	 * 	Returns a map which contains all OWL namespaces which are provided by the component.
	 * 	The keys are the abbreviations, and the values are the full URIs of these namespaces.
	 */
	public Map<String, String> getProvidedNamespaces();
}
