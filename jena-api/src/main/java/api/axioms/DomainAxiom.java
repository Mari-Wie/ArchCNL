package api.axioms;

import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;

import datatypes.RuleType;

public class DomainAxiom implements CustomAxiom {

	private String iriPath;
	private String property;
	private String subject;

	private OWLObjectPropertyDomainAxiom domainAxiom;

	public DomainAxiom(String iriPath, String property, String subject) {
		this.iriPath = iriPath;
		this.property = property;
		this.subject = subject;
	}

	public DomainAxiom(String subject, String property) {
		this.property = property;
		this.subject = subject;
	}

	public DomainAxiom(OWLObjectPropertyDomainAxiom domainAxiom) {
		this.domainAxiom = domainAxiom;
	}

	public String getAxiomString() {
		 return "AxiomConstraint{<"+iriPath+"#"+property+"> rdfs:domain <"+iriPath+"#"+subject+">}";

	}

	public RuleType getRuleType() {
		return RuleType.DOMAIN;
	}

	public int getCount() {
		return 0;
	}

}
