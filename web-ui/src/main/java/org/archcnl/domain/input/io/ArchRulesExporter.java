package org.archcnl.domain.input.io;

import java.io.File;
import java.io.IOException;
import org.archcnl.domain.input.datatypes.RulesAndMappings;

public interface ArchRulesExporter {

    public void writeArchitectureRules(File file, RulesAndMappings rulesAndMappings)
            throws IOException;
}
