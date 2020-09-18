package api.axioms;

import datatypes.RuleType;

public class ExistentialAxiom implements CustomAxiom {
	
	private String iriPath;
	private String subject;
	private String object;
	private String property;

	public ExistentialAxiom(String iriPath, String subjectName, String objectName, String property) {
		this.iriPath = iriPath;
		this.subject = subjectName;
		this.object = objectName;
		this.property = property;
	}

	public String getAxiomString() {
		return "AxiomConstraint{<"+iriPath+"#"+subject+"> rdfs:subClassOf (<"+iriPath+"#"+property+"> some <"+iriPath+"#"+object+">)}";
	}

	public RuleType getRuleType() {
		return RuleType.EXISTENTIAL;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
