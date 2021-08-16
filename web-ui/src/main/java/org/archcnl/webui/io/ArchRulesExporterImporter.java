package org.archcnl.webui.io;

import java.io.File;

import org.archcnl.webui.datatypes.RulesAndMappings;

public interface ArchRulesExporterImporter {

    public void writeArchitectureRules(File file, RulesAndMappings rulesAndMappings);

    public RulesAndMappings readArchitectureRules(File file);
}
