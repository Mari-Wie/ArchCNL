package api.axioms;

import datatypes.RuleType;

public class ConditionalAxiom implements CustomAxiom {
	
	private String iriPath;
	private String subProperty;
	private String superProperty;

	public ConditionalAxiom(String iriPath, String subProp, String superProp) {
		this.iriPath = iriPath;
		this.subProperty = subProp;
		this.superProperty = superProp;
	}

	public String getAxiomString() {
		return "AxiomConstraint{<"+iriPath+"#"+subProperty+"> rdfs:subPropertyOf <"+iriPath+"#"+superProperty+">}";
	}

	public RuleType getRuleType() {
		return RuleType.CONDITIONAL;
	}

	public int getCount() {
		return 0;
	}

}
