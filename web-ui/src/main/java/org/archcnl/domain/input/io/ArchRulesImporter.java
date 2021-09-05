package org.archcnl.domain.input.io;

import java.io.File;
import java.io.IOException;

import org.archcnl.domain.input.datatypes.RulesConceptsAndRelations;

public interface ArchRulesImporter {

    public void readArchitectureRules(
            File file, RulesConceptsAndRelations rulesConceptsAndRelations) throws IOException;
}
