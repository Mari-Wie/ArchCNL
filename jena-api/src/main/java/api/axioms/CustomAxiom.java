package api.axioms;

import datatypes.RuleType;

public interface CustomAxiom {
	
	public RuleType getRuleType();
	public String getAxiomString();
	public int getCount();

}
