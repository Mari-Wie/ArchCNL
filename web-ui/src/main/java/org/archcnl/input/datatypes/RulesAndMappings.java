package org.archcnl.input.datatypes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.archcnl.input.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.input.datatypes.mappings.ConceptMapping;
import org.archcnl.input.datatypes.mappings.Mapping;
import org.archcnl.input.datatypes.mappings.RelationMapping;

public class RulesAndMappings {

    private List<ConceptMapping> conceptMappings;
    private List<RelationMapping> relationMappings;
    private List<ArchitectureRule> architectureRules;

    public RulesAndMappings() {
        conceptMappings = new LinkedList<>();
        relationMappings = new LinkedList<>();
        architectureRules = new LinkedList<>();
    }

    public RulesAndMappings(
            List<ArchitectureRule> architectureRules,
            List<ConceptMapping> conceptMappings,
            List<RelationMapping> relationMappings) {
        this.conceptMappings = conceptMappings;
        this.relationMappings = relationMappings;
        this.architectureRules = architectureRules;
    }

    public void addConceptMapping(ConceptMapping conceptMapping) {
        conceptMappings.add(conceptMapping);
    }

    public void addRelationMapping(RelationMapping relationMapping) {
        relationMappings.add(relationMapping);
    }

    public void addArchitectureRule(ArchitectureRule architectureRule) {
        architectureRules.add(architectureRule);
    }

    public List<ConceptMapping> getConceptMappings() {
        return conceptMappings;
    }

    public List<RelationMapping> getRelationMappings() {
        return relationMappings;
    }

    public List<ArchitectureRule> getArchitectureRules() {
        return architectureRules;
    }

    public List<Mapping> getMappings() {
        List<Mapping> mappings = new ArrayList<>(getConceptMappings());
        mappings.addAll(getRelationMappings());
        return mappings;
    }
}
