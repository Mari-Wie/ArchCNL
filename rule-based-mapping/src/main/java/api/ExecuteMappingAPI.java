package api;

import java.io.FileNotFoundException;

public interface ExecuteMappingAPI {

	public void setReasoningConfiguration(ReasoningConfiguration config);
	public void executeMapping() throws FileNotFoundException;
	
}
