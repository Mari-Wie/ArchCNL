package org.archcnl.architecturereasoning.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for a mapper between architectural concepts and code artifacts.
 * 
 * Value object.
 */
public class ReasoningConfiguration {
	
	private final String pathToData;
	private final String pathToMappingRules;
	private final String resultPath;
	private final List<String> pathsToConcepts;
	
	/**
	 * 
	 * @param pathToData
	 * @param pathToMappingRules
	 * @param resultPath
	 */
	private ReasoningConfiguration(String pathToData, String pathToMappingRules, String resultPath, List<String> pathToConcepts) {
		this.pathToData = pathToData;
		this.pathToMappingRules = pathToMappingRules;
		this.resultPath = resultPath;
		this.pathsToConcepts = new ArrayList<>(pathToConcepts);
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
	 */
	public String getResultPath() {
		return resultPath;
	}
	
	/**
	 * Returns a list of all "concept file" paths. See {@link #withPathsToConcepts(List)}
	 * for further details.
	 */
	public List<String> getPathsToConcepts() {
		return new ArrayList<>(pathsToConcepts);
	}
	
	/**
	 * Returns a new builder for this class.
	 */
	public static ReasoningConfigurationBuilder builder() {
		return new ReasoningConfigurationBuilder();
	}
	
	public static class ReasoningConfigurationBuilder {
		private String pathToData;
		private String pathToMappingRules;
		private String resultPath;
		private List<String> pathsToConcepts;
		
		public ReasoningConfigurationBuilder() {
			pathsToConcepts = new ArrayList<>();
		}
		
		/**
		 * Sets the input code model ontology to be used by the mapper.
		 * @param data The path to the OWL ontology file which contains the code model.
		 * @return this instance
		 */
		public ReasoningConfigurationBuilder withData(String data) {
			this.pathToData = data;
			return this;
		}
		
		/**
		 * Sets the input mapping rules file to be used by the mapper.
		 * @param rules The path to the SWRL file containing the architecture-to-code mapping.
		 * @return this instance
		 */
		public ReasoningConfigurationBuilder withMappingRules(String rules) {
			this.pathToMappingRules = rules;
			return this;
		}
		
		/**
		 * Sets the path of the output ontology file.
		 * @param result the path to the OWL ontology file in which the resulting mapped model will be stored by the mapper.
		 * @return this instance
		 */
		public ReasoningConfigurationBuilder withResult(String result) {
			this.resultPath = result;
			return this;
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
		public ReasoningConfigurationBuilder withPathsToConcepts(List<String> paths) {
			this.pathsToConcepts.addAll(paths);
			return this;
		}
		
		/**
		 * Creates a new instance.
		 */
		public ReasoningConfiguration build() {
			return new ReasoningConfiguration(pathToData, pathToMappingRules, resultPath, pathsToConcepts);
		}
	}

}
