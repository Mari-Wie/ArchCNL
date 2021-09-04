package org.archcnl.domain.input.io;

import java.io.File;
import java.io.IOException;
import org.archcnl.domain.input.datatypes.RulesAndMappings;
import org.archcnl.domain.input.datatypes.mappings.Relation;

public interface ArchRulesImporter {

    RulesAndMappings readArchitectureRules(
            File file, Relation typeRelation, Relation matchesRelation) throws IOException;
}
