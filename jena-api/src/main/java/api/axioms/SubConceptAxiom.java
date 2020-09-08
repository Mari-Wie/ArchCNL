package api.axioms;

import datatypes.RuleType;

public class SubConceptAxiom implements CustomAxiom {
	
	private String subject;
	private String object;

	public SubConceptAxiom(String subject, String object) {
		this.subject = subject;
		this.object = object;
	}

	public String getAxiomString() {
		//return "AxiomConstraint{<"+subject+">" + " rdfs:subClassOf " + "<"+object+">}";
		return "AxiomConstraint{" + subject + " rdfs:subClassOf " + object + "}";
	}

	public RuleType getRuleType() {
		return RuleType.SUB_CONCEPT;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
