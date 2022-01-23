package org.archcnl.domain.common.io;

import java.io.File;
import java.io.IOException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public interface ArchRulesImporter {

    public void readArchitectureRules(
            File file, RulesConceptsAndRelations rulesConceptsAndRelations) throws IOException;
}
