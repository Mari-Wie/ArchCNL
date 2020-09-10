package api.axioms;

import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;

import datatypes.RuleType;

public class RangeAxiom implements CustomAxiom {
	
	private String iriPath;
	private String property;
	private String object;
	
	public RangeAxiom(String iriPath, String property, String object) {
		super();
		this.iriPath = iriPath;
		this.property = property;
		this.object = object;
	}

	public RangeAxiom(String object, String property) {
		this.property = property;
		this.object = object;
	}

	public RangeAxiom(OWLObjectPropertyRangeAxiom rangeAxiom) {
		// TODO Auto-generated constructor stub
	}

	public String getAxiomString() {
		return "AxiomConstraint{"+property+" rdfs:range "+object+"}";
	}

	public RuleType getRuleType() {
		return RuleType.RANGE;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
