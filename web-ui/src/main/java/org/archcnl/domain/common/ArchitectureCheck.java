package org.archcnl.domain.common;

import com.complexible.stardog.StardogException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
import org.archcnl.domain.common.io.AdocExporter;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.domain.output.repository.ResultRepositoryImpl;
import org.archcnl.toolchain.CNLToolchain;

public class ArchitectureCheck {

    private ResultRepository repository;
    private List<String> sourcePaths;
    private final String ruleFile = ConfigAppService.getDbRuleFile();
    private static final boolean VERBOSE_LOGGING = false;
    private static final boolean REMOVE_DBS = true;
    private final List<String> enabledParsers = Arrays.asList("java", "git");

    public ArchitectureCheck() throws PropertyNotFoundException {
        this.repository =
                new ResultRepositoryImpl(
                        ConfigAppService.getDbUrl(),
                        ConfigAppService.getDbName(),
                        ConfigAppService.getDbUsername(),
                        ConfigAppService.getDbPassword());
    }

    public void runToolchain(
            String path,
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager)
            throws IOException, PropertyNotFoundException, StardogException {
        writeRuleFile(ruleManager, conceptManager, relationManager);
        setProjectPath(path);
        createDbWithViolations();
    }

    private void writeRuleFile(
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager)
            throws IOException {
        final File file = new File(ruleFile);
        AdocExporter adocExporter = new AdocExporter();
        adocExporter.writeToAdoc(
                file,
                ruleManager,
                conceptManager,
                relationManager,
                new LinkedList<>(),
                new LinkedList<>());
    }

    private void createDbWithViolations() throws PropertyNotFoundException {
        CNLToolchain.runToolchain(
                ConfigAppService.getDbName(),
                ConfigAppService.getDbUrl(),
                ConfigAppService.getDbContext(),
                ConfigAppService.getDbUsername(),
                ConfigAppService.getDbPassword(),
                sourcePaths,
                ruleFile,
                ArchitectureCheck.VERBOSE_LOGGING,
                ArchitectureCheck.REMOVE_DBS,
                enabledParsers);
    }

    private void setProjectPath(String projectPath) {
        sourcePaths = Arrays.asList(projectPath);
    }

    public ResultRepository getRepository() {
        return repository;
    }
}
