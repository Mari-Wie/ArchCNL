package api.axioms;

import datatypes.RuleType;

public class MinCardinalityRestrictionAxiom implements CustomAxiom {

	private String subject;
	private String object;
	private String property;
	private String count;
	private String iriPath;
	
	public MinCardinalityRestrictionAxiom(String iriPath, String subject, String object, String property, String count) {
		this.iriPath = iriPath;
		this.subject = subject;
		this.object = object;
		this.property = property;
		this.count = count;
	}

	public String getAxiomString() {
		return "AxiomConstraint{<"+iriPath+"#"+subject+"> rdfs:subClassOf "+ "(<"+iriPath+"#"+property+"> min "+count+ " <"+iriPath+"#"+object+">)}";
	}

	public RuleType getRuleType() {
		return RuleType.AT_LEAST;
	}

	public int getCount() {
		return Integer.parseInt(count);
	}

}
