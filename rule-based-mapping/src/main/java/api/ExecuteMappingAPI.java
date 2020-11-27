package api;

import java.io.FileNotFoundException;

public interface ExecuteMappingAPI {

	public void setReasoningConfiguration(ReasoningConfiguration config, String outputFilePath);
	public void executeMapping() throws FileNotFoundException;
	public String getReasoningResultPath();
	
}
