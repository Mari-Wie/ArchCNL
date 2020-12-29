package datatypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Singleton class used to store architecture rules.
 *
 */
public class ArchitectureRules {

	private static final Logger LOG = LogManager.getLogger(ArchitectureRules.class);
	private static ArchitectureRules instance;
	private Map<Integer, ArchitectureRule> rules; // using a mutable type as key can cause strange behavior, it did so in the past

	private ArchitectureRules() {

		rules = new HashMap<>();
	}

	/**
	 * returns the singleton instance
	 */
	public static ArchitectureRules getInstance() {
		if (instance == null) {
			instance = new ArchitectureRules();
		}
		return instance;
	}

	/**
	 * Adds the given architecture rule
	 * @param r the rule to add
	 * @param id the ID of the rule
	 * @param path path to an OWL file which contains the rule as OWL constraints
	 */
	public void addRuleWithPathToConstraint(ArchitectureRule r, int id, String path) {
		r.setContraintFile(path);
		rules.put(id, r);
	}
	
	/**
	 * @param id the ID of the rule to return
	 * @return the rule with the given ID, or null, if no rule with the given ID exists
	 */
	public ArchitectureRule getRuleWithID(int id) {
		LOG.trace("getRuleWithID id: " + id);
		return rules.get(id);
	}
	
	/**
	 * @return a list of all currently stored architecture rules 
	 */
	public List<ArchitectureRule> getRules() {
		return new ArrayList<>(rules.values()); 
	}
}
