package org.archcnl.webui.io;

import java.io.File;
import java.io.IOException;
import org.archcnl.webui.datatypes.RulesAndMappings;

public interface ArchRulesImporter {

    public RulesAndMappings readArchitectureRules(File file) throws IOException;
}
