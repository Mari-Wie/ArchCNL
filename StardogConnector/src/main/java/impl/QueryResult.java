package impl;

import java.util.HashMap;
import java.util.Map;

public class QueryResult {
	
	private Map<String, String> variable2Value;
	
	public QueryResult() {
		variable2Value = new HashMap<>();
	}
	
	public Map<String,String> getResult() {
		return variable2Value;
	}

	public void addResultTuple(String variable, String value) {
		variable2Value.put(variable, value);
	}
	
	public String getValueFor(String variable) {
		return variable2Value.get(variable);
	}
	
}
