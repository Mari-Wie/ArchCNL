package org.archcnl.domain.input.model.presets.microservicearchitecture;

import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;

public interface ArchitecturalStyle {

    void createMappings();

    void createArchitecturalRules();

    /**
     * Adds all the mappings and rules for the microservice architectural style.
     *
     * @param relationManager the relation manager to add the relations to.
     * @param conceptManager the concept manager to add the concepts to.
     * @param ruleManager the rule manager to add the architecture rules to.
     */
    void createRulesAndMappings(
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager);
}
