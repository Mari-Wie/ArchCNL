package datatypes;

import java.util.HashMap;
import java.util.Map;

public class ArchitectureRules {
 
	private static ArchitectureRules instance;
	private Map<ArchitectureRule, Integer> rules;
	private Map<ArchitectureRule, String> pathToConvertedConstraint;

	public ArchitectureRules() {

		rules = new HashMap<>();
		pathToConvertedConstraint = new HashMap<>();

	}

	public static ArchitectureRules getInstance() {
		if (instance == null) {
			instance = new ArchitectureRules();
		}
		return instance;
	}

	public void addRule(ArchitectureRule r, int id) {
		rules.put(r, id);
	}
	
	public void addRuleWithPathToConstraint(ArchitectureRule r, int id, String path) {
		rules.put(r, id);
		pathToConvertedConstraint.put(r, path);
	}
	
	public String getPathOfConstraintForRule(ArchitectureRule r) {
		return pathToConvertedConstraint.get(r);
	}
	
	public ArchitectureRule getRuleWithID(int id) {
		for (ArchitectureRule rule : rules.keySet()) {
			if(rules.get(rule) == id) {
				return rule;
			}
		}
		return null;
	}
	
	public Map<ArchitectureRule,Integer> getRules() {
		return rules;
	}
	
	public int getIDOfRule(String constraint) {
		for (ArchitectureRule r : rules.keySet()) {
			if(r.getOwlAxiom().equals(constraint)) {
				return rules.get(r);
			}
			else if(r.getSecondOWLAxiom()!= null && r.getSecondOWLAxiom().equals(constraint)) {
				return rules.get(r);
			}
		}
		return -1;
	}
	
	public ArchitectureRule getRuleWithConstraint(String constraint) {
		for (ArchitectureRule r : rules.keySet()) {
			if(r.getOwlAxiom().equals(constraint)) {
				return r;
			}
			else if(r.getSecondOWLAxiom()!= null && r.getSecondOWLAxiom().equals(constraint)) {
				return r;
			}
		}
		return null;
	}
	
	public ArchitectureRule getRuleByCNLSentence(String cnlSentence) {
		for (ArchitectureRule r : rules.keySet()) {
			if(r.getCnlSentence().equals(cnlSentence)) {
				return r;
			}
		}
		
		return null;
	}

	public void print() {
		for (ArchitectureRule rule : rules.keySet()) {
			System.out.println("id: " + rules.get(rule));
			System.out.println(rule.getCnlSentence());
//			System.out.println(rule.getOwlAxiom());
//			if(rule.getSecondOWLAxiom()!=null) {
//				System.out.println(rule.getSecondOWLAxiom());
//			}
		}
	}

}
