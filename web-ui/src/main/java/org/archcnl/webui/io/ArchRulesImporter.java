package org.archcnl.webui.io;

import java.io.File;
import java.io.IOException;

import org.archcnl.webui.datatypes.RulesAndMappings;
import org.archcnl.webui.datatypes.mappings.Relation;

public interface ArchRulesImporter {

	RulesAndMappings readArchitectureRules(File file, Relation typeRelation) throws IOException;
	
}
