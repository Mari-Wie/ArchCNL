package impl;

import java.util.ArrayList;
import java.util.List;

public class QueryResultSet {

	private List<QueryResult> results;
	
	public QueryResultSet() {
		results = new ArrayList<>();
	}
	
	public void addResult(QueryResult result) {
		results.add(result);
	}

	public List<QueryResult> getResults() {
		return results;
	}
	
	public void print() {
		int i = 1;
		for (QueryResult queryResult : results) {
			System.out.println("Result " + i);
			for (String variable : queryResult.getResult().keySet()) {
				System.out.println(variable + " " + queryResult.getResult().get(variable));
			}
			i++;
		}
	}
	
}
