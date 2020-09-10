package api;

import java.util.ArrayList;
import java.util.List;

public class ReasoningConfiguration {
	
	private String pathToData;
	private String pathToMappingRules;
	private String resultPath;
	private String pathToConcepts;
	
	private List<String> pathsToConcepts; //in case several files are used for the same ontology
	
	private ReasoningConfiguration() {
		// TODO Auto-generated constructor stub
		pathsToConcepts = new ArrayList<String>();
	}
	
	public static ReasoningConfiguration build() {
		return new ReasoningConfiguration();
	}
	
	public ReasoningConfiguration withData(String data) {
		this.pathToData = data;
		return this;
	}
	
	public ReasoningConfiguration withMappingRules(String rules) {
		this.pathToMappingRules = rules;
		return this;
	}
	
	public ReasoningConfiguration withResult(String result) {
		this.resultPath = result;
		return this;
	}

	public String getPathToData() {
		return pathToData;
	}

	public String getPathToMappingRules() {
		return pathToMappingRules;
	}

	public String getResultPath() {
		return resultPath;
	}

	public ReasoningConfiguration withConcepts(String concepts) {
		pathToConcepts = concepts;
		return this;
	}
	
	public String getPathToConcepts() {
		return pathToConcepts;
	}
	
	public ReasoningConfiguration addPathsToConcepts(List<String> paths) {
		this.pathsToConcepts.addAll(paths);
		return this;
	}
	
	public List<String> getPathsToConcepts() {
		return pathsToConcepts;
	}

}
