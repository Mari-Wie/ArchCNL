package datatypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArchitectureRules {

	private static final Logger LOG = LogManager.getLogger(ArchitectureRules.class);
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
        LOG.info("\n -- AddRule --------------------- \n"+r.toString());
		rules.put(r, id);
	}
	
	public void addRuleWithPathToConstraint(ArchitectureRule r, int id, String path) {
		rules.put(r, id);
		pathToConvertedConstraint.put(r, path);
	}
	
	public String getPathOfConstraintForRule(ArchitectureRule r) {
		return pathToConvertedConstraint.get(r);
	}
	
	/**
	 * @param id the ID of the rule to return
	 * @return the rule with the given ID, or null, if no rule with the given ID exists
	 */
	public ArchitectureRule getRuleWithID(int id) {
		for (ArchitectureRule rule : rules.keySet()) {
			if(rules.get(rule) == id) {
				return rule;
			}
		}
		return null;
	}
	
	/**
	 * @return a list of all currently stored architecture rules 
	 */
	public List<ArchitectureRule> getRules() {
		return new ArrayList<>(rules.keySet());
	}
}
