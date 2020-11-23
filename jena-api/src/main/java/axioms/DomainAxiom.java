package axioms;

import datatypes.RuleType;

public class DomainAxiom implements CustomAxiom {

	private String iriPath;
	private String property;
	private String subject;

	public DomainAxiom(String iriPath, String property, String subject) {
		this.iriPath = iriPath;
		this.property = property;
		this.subject = subject;
	}

	public DomainAxiom(String subject, String property) {
		this.property = property;
		this.subject = subject;
	}

	public String getAxiomString() {
		return "AxiomConstraint{<" + iriPath + "#" + property + "> rdfs:domain <" + iriPath + "#" + subject + ">}";

	}

	public RuleType getRuleType() {
		return RuleType.DOMAIN;
	}

	public int getCount() {
		return 0;
	}

}
