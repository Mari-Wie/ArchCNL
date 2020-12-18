package api;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for a mapper between architectural concepts and code artifacts.
 */
public class ReasoningConfiguration {
	
	private String pathToData;
	private String pathToMappingRules;
	private String resultPath;
	
	private List<String> pathsToConcepts; //in case several files are used for the same ontology
	
	// attempt to use the builder pattern
	// TODO: issue #63, update comments
	private ReasoningConfiguration() {
		pathsToConcepts = new ArrayList<String>();
	}
	
	/**
	 * Creates a new instance.
	 */
	public static ReasoningConfiguration build() {
		return new ReasoningConfiguration();
	}
	
	/**
	 * Sets the input code model ontology to be used by the mapper.
	 * @param data The path to the OWL ontology file which contains the code model.
	 * @return this instance
	 */
	public ReasoningConfiguration withData(String data) {
		this.pathToData = data;
		return this;
	}
	
	/**
	 * Sets the input mapping rules file to be used by the mapper.
	 * @param rules The path to the SWRL file containing the architecture-to-code mapping.
	 * @return this instance
	 */
	public ReasoningConfiguration withMappingRules(String rules) {
		this.pathToMappingRules = rules;
		return this;
	}
	
	/**
	 * Sets the path of the output ontology file.
	 * @param result the path to the OWL ontology file in which the resulting mapped model will be stored by the mapper.
	 * @return this instance
	 */
	public ReasoningConfiguration withResult(String result) {
		this.resultPath = result;
		return this;
	}

	/**
	 * Returns the path to the input code model ontology file (OWL format).
	 */
	public String getPathToData() {
		return pathToData;
	}

	/**
	 * Returns the path to the input architecture-to-code mapping rule (SWRL format).
	 */
	public String getPathToMappingRules() {
		return pathToMappingRules;
	}

	/**
	 * Returns the path to the output ontology file (OWL format).
	 * @return
	 */
	public String getResultPath() {
		return resultPath;
	}
	
	/**
	 * It is currently unclear what is a "concept" in the sense of this method. 
	 * However, files containing the architecture rules as OWL constraints are
	 * added with this method. So make sure that you add them here. These
	 * concepts are currently added to the output ontology.
	 * @param paths 
	 * 		A list of paths to "concept" files. This list should contain the
	 * 		paths of all architecture rule files (stored as OWL constraints).
	 * @return this instance
	 */
	public ReasoningConfiguration withPathsToConcepts(List<String> paths) {
		this.pathsToConcepts.addAll(paths);
		return this;
	}
	
	/**
	 * Returns a list of all "concept file" paths. See {@link #withPathsToConcepts(List)}
	 * for further details.
	 */
	public List<String> getPathsToConcepts() {
		return pathsToConcepts;
	}

}
