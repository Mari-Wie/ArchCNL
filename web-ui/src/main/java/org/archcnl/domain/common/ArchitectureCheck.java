package org.archcnl.domain.common;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
import org.archcnl.domain.input.io.ArchRulesToAdocWriter;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.domain.output.repository.ResultRepositoryImpl;
import org.archcnl.toolchain.CNLToolchain;

public class ArchitectureCheck {

    private static final Logger LOG = LogManager.getLogger(ArchitectureCheck.class);

    private ResultRepository repository;
    
    private final String rootDir = "D:/Programme/ArchCNL/ArchCNL/toolchain/src/integration-test/resources/";
    private final List<String> sourcePaths = Arrays.asList(rootDir + "OnionArchitectureDemo/src/");
    private final String ruleFile = rootDir + "OnionArchitectureDemo/rules.adoc";
    private final boolean verboseLogging = false;
    private final boolean removeDBs = true;
    private final List<String> enabledParsers = Arrays.asList("java");

    public ArchitectureCheck() throws PropertyNotFoundException {
    	
            this.repository =
                    new ResultRepositoryImpl(
                            ConfigAppService.getDbUrl(),
                            ConfigAppService.getDbName(),
                            ConfigAppService.getDbUsername(),
                            ConfigAppService.getDbPassword());
        }
    
    public void writeRuleFile() {
        final File file = new File("temp/GeneratedRuleFile.adoc");
        ArchRulesToAdocWriter archRulesToAdocWriter = new ArchRulesToAdocWriter();
        try {
			archRulesToAdocWriter.writeArchitectureRules(file, RulesConceptsAndRelations.getInstance());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void createDbWithViolations() throws PropertyNotFoundException {
    	CNLToolchain.runToolchain(ConfigAppService.getDbName(),
			ConfigAppService.getDbUrl(),
			ConfigAppService.getDbContext(),
            ConfigAppService.getDbUsername(),
            ConfigAppService.getDbPassword(),
            sourcePaths,
            ruleFile,
            verboseLogging, 
            removeDBs,
            enabledParsers);
    }

    public ResultRepository getRepository() {
        return repository;
    }
}
