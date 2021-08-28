package org.archcnl.input.io;

import java.io.File;
import java.io.IOException;
import org.archcnl.input.datatypes.RulesAndMappings;
import org.archcnl.input.datatypes.mappings.Relation;

public interface ArchRulesImporter {

    RulesAndMappings readArchitectureRules(
            File file, Relation typeRelation, Relation matchesRelation) throws IOException;
}
