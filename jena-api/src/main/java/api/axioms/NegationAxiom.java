package api.axioms;

import datatypes.RuleType;

public class NegationAxiom implements CustomAxiom {
	
	private String subject;
	private String object;
	
	public NegationAxiom(String subject, String object) {
	
		this.subject = subject;
		this.object = object;
	
	}

	public RuleType getRuleType() {
		return RuleType.NEGATION;
	}

	public String getAxiomString() {
		return "AxiomConstraint{" + subject + " rdfs:subClassOf " + object + "}";
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
