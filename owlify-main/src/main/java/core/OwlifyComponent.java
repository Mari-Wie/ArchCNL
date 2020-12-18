package core;

import java.util.List;
import java.util.Map;

/**
 * Interface for input parsers. Each parser takes some files as input, parses them into an OWL ontology
 * and writes this ontology into an output file.
 */
public interface OwlifyComponent {
	/**
	 * This method performs the parsing step. Make sure that all input files have been added
	 * before calling this method (via {@link #addSourcePath(String)}). 
	 * The inputs will be read and the resulting output ontology
	 * will be written to the file specified by {@link #getResultPath()}.
	 */
	public void transform();
	
	/**
	 * Returns the path to the parser's output file which contains
	 * the generated OWL ontology.
	 */
	public String getResultPath();
	
	/**
	 * Returns a list of input file/directory path's which have been added in the past.
	 */
	public List<String> getSourcePaths();
	
	/**
	 * Adds a file (or directory) to this parser's inputs.
	 * @param path the path of the file/directory to add
	 */
	public void addSourcePath(String path);
	
	/**
	 * @return 
	 * 	Returns a map which contains all OWL namespaces which are provided by the component.
	 * 	The keys are the abbreviations, and the values are the full URIs of these namespaces.
	 */
	public Map<String, String> getProvidedNamespaces();
}
