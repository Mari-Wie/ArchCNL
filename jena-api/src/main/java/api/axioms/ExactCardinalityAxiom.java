package api.axioms;

import datatypes.RuleType;

public class ExactCardinalityAxiom implements CustomAxiom {
	
	private String iriPath;
	private String subject;
	private String object;
	private String property;
	private String count;

	public ExactCardinalityAxiom(String iriPath, String subjectName, String objectName, String property, String count) {
		this.iriPath = iriPath;
		this.subject = subjectName;
		this.object = objectName;
		this.property = property;
		this.count = count;
	}

	public String getAxiomString() {
		return "AxiomConstraint{<"+iriPath+"#"+subject+"> rdfs:subClassOf "+ "(<"+iriPath+"#"+property+"> exactly "+count+ " <"+iriPath+"#"+object+">)}";
	}

	public RuleType getRuleType() {
		return RuleType.EXACTLY;
	}

	public int getCount() {
		return Integer.parseInt(count);
	}

}
