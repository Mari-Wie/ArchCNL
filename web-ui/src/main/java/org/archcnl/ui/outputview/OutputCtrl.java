package org.archcnl.ui.outputview;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.domain.output.repository.ResultRepositoryImpl;
import org.archcnl.toolchain.CNLToolchain;

public class OutputCtrl {

    private static final Logger LOG = LogManager.getLogger(OutputCtrl.class);

    private ResultRepository repository;
    
    private final String rootDir = "D:/Programme/ArchCNL/ArchCNL/toolchain/src/integration-test/resources/";
    private final String database = "archcnl_it_db";
    private final String server = "http://localhost:5820";
    private final String username = "admin";
    private final String password = "admin";
    private final String context = "http://graphs.org/" + database + "/1.0";
    private final String resultPath = rootDir + "result.owl";
    private final List<String> sourcePaths = Arrays.asList(rootDir + "OnionArchitectureDemo/src/");
    private final String ruleFile = rootDir + "OnionArchitectureDemo/rules.adoc";
    private final boolean verboseLogging = false;
    private final boolean removeDBs = true;
    private final List<String> enabledParsers = Arrays.asList("java");

    public OutputCtrl() {
        try {
            this.repository =
                    new ResultRepositoryImpl(
                            ConfigAppService.getDbUrl(),
                            ConfigAppService.getDbName(),
                            ConfigAppService.getDbUsername(),
                            ConfigAppService.getDbPassword());
        } catch (final PropertyNotFoundException e) {
            OutputCtrl.LOG.error(e.getMessage() + " Output controller can not be initialized.");
            e.printStackTrace();
        }
    }
    
    public void checkForViolations() throws PropertyNotFoundException {
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
