package org.archcnl.webui.datatypes;

import org.archcnl.webui.datatypes.architecturerules.ArchitectureRuleManager;
import org.archcnl.webui.datatypes.mappings.ConceptManager;
import org.archcnl.webui.datatypes.mappings.RelationManager;
import org.archcnl.webui.exceptions.ConceptDoesNotExistException;

public class RulesConceptsAndRelations {
	
	private static final RulesConceptsAndRelations INSTANCE;
	static {
		try {
			INSTANCE = new RulesConceptsAndRelations();
		} catch (ConceptDoesNotExistException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

    private ArchitectureRuleManager architectureRuleManager;
    private ConceptManager conceptManager;
    private RelationManager relationManager;

    private RulesConceptsAndRelations() throws ConceptDoesNotExistException {
    	architectureRuleManager = new ArchitectureRuleManager();
    	conceptManager = new ConceptManager();
    	relationManager = new RelationManager();
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
