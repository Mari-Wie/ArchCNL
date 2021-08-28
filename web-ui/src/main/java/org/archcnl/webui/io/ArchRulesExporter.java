package org.archcnl.webui.io;

import java.io.File;
import java.io.IOException;
import org.archcnl.webui.datatypes.RulesAndMappings;

public interface ArchRulesExporter {

    public void writeArchitectureRules(File file, RulesAndMappings rulesAndMappings)
            throws IOException;
}
