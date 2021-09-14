package org.archcnl.domain.input.model;

import com.google.common.annotations.VisibleForTesting;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.input.model.mappings.ConceptManager;
import org.archcnl.domain.input.model.mappings.RelationManager;

public class RulesConceptsAndRelations {

    private static final RulesConceptsAndRelations INSTANCE;

    static {
        try {
            INSTANCE = new RulesConceptsAndRelations();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    private ArchitectureRuleManager architectureRuleManager;
    private ConceptManager conceptManager;
    private RelationManager relationManager;

    @VisibleForTesting
    public RulesConceptsAndRelations() throws ConceptDoesNotExistException {
        architectureRuleManager = new ArchitectureRuleManager();
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    public ArchitectureRuleManager getArchitectureRuleManager() {
        return architectureRuleManager;
    }

    public ConceptManager getConceptManager() {
        return conceptManager;
    }

    public RelationManager getRelationManager() {
        return relationManager;
    }

    public static RulesConceptsAndRelations getInstance() {
        return INSTANCE;
    }
}
