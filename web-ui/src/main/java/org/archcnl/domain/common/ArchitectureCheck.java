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
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.domain.output.repository.ResultRepositoryImpl;
import org.archcnl.toolchain.CNLToolchain;

public class ArchitectureCheck {

    private ResultRepository repository;
    private List<String> sourcePaths;
    private final String ruleFile = ConfigAppService.getDbRuleFile();
    private final boolean verboseLogging = false;
    private final boolean removeDBs = true;
    private final List<String> enabledParsers = Arrays.asList("java");

    public ArchitectureCheck(String path)
            throws PropertyNotFoundException, StardogException, IOException {
        this.repository =
                new ResultRepositoryImpl(
                        ConfigAppService.getDbUrl(),
                        ConfigAppService.getDbName(),
                        ConfigAppService.getDbUsername(),
                        ConfigAppService.getDbPassword());
        writeRuleFile();
        setProjectPath(path);
        createDbWithViolations();
    }

    public void writeRuleFile() throws IOException {
        final File file = new File(ruleFile);
        AdocExporter adocExporter = new AdocExporter();
        adocExporter.writeToAdoc(
                file,
                RulesConceptsAndRelations.getInstance().getArchitectureRuleManager(),
                RulesConceptsAndRelations.getInstance().getConceptManager(),
                RulesConceptsAndRelations.getInstance().getRelationManager(),
                new LinkedList<>(),
                new LinkedList<>());
    }

    public void createDbWithViolations() throws PropertyNotFoundException {
        CNLToolchain.runToolchain(
                ConfigAppService.getDbName(),
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

    public void setProjectPath(String projectPath) {
        sourcePaths = Arrays.asList(projectPath);
    }

    public ResultRepository getRepository() {
        return repository;
    }
}
