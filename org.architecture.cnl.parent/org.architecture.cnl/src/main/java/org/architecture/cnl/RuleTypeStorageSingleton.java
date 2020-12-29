package org.architecture.cnl;

import java.util.HashMap;
import java.util.Map;

import datatypes.RuleType;

/**
 * This singleton class is used by the ArchcnlGenerator to communicate the
 * RuleType of the parsed architecture rules to the CNL2OWLGenerator. 
 */
public class RuleTypeStorageSingleton {
	private static RuleTypeStorageSingleton instance = new RuleTypeStorageSingleton();
	
	private Map<Integer, RuleType> ruleTypes;
	
	private RuleTypeStorageSingleton() {
		ruleTypes = new HashMap<>();
	}
	
	/**
	 * Returns the singleton instance.
	 */
	public static RuleTypeStorageSingleton getInstance() {
		return instance;
	}

	/**
	 * Stores the information that the given rule has the given rule type.
	 * @param ruleId The id of the rule.
	 * @param type The type of the rule.
	 */
	public void storeTypeOfRule(int ruleId, RuleType type) {
		ruleTypes.put(ruleId, type);
		
	}

	/**
	 * Returns the rule type of the given rule, or null if no rule type has been stored for this rule.
	 * @param ruleId The ID of the rule to query.
	 * @return The rule's type or null, when no type is stored for the rule.
	 */
	public RuleType retrieveTypeOfRule(int ruleId) {
		return ruleTypes.get(ruleId);
	}
	
}
