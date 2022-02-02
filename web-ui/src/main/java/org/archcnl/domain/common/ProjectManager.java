package org.archcnl.domain.common;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import org.archcnl.domain.common.io.AdocExporter;
import org.archcnl.domain.common.io.AdocImporter;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;

public class ProjectManager {

    private static final String DEFAULT_PROJECT_FILE_NAME = "ArchCNL-architecture_rules.adoc";
    private final Queue<FreeTextQuery> freeTextQueryQueue = new LinkedList<>();
    private final Queue<Query> customQueryQueue = new LinkedList<>();
    private Optional<File> projectFile;

    public ProjectManager() {
        this.projectFile = Optional.empty();
    }

    public Optional<File> getProjectFile() {
        return projectFile;
    }

    public void openProject(File file) throws IOException {
        AdocImporter importer = new AdocImporter();
        importer.readFromAdoc(
                file,
                RulesConceptsAndRelations.getInstance().getArchitectureRuleManager(),
                RulesConceptsAndRelations.getInstance().getConceptManager(),
                RulesConceptsAndRelations.getInstance().getRelationManager(),
                freeTextQueryQueue,
                customQueryQueue);
        this.projectFile = Optional.of(file);
    }

    /**
     * Writes the project to the specified projectFile. Does nothing when projectFile is not set
     * This function must only be called when a project is opened using the
     * ProjectManager.openProject function
     *
     * @throws IOException when file cannot be written
     */
    public void saveProject(List<Query> customQueries, List<FreeTextQuery> freeTextQueries)
            throws IOException {
        if (projectFile.isPresent()) {
            AdocExporter exporter = new AdocExporter();
            exporter.writeToAdoc(
                    projectFile.get(),
                    RulesConceptsAndRelations.getInstance().getArchitectureRuleManager(),
                    RulesConceptsAndRelations.getInstance().getConceptManager(),
                    RulesConceptsAndRelations.getInstance().getRelationManager(),
                    customQueries,
                    freeTextQueries);
        }
    }

    public void saveProject(
            File file, List<Query> customQueries, List<FreeTextQuery> freeTextQueries)
            throws IOException {
        AdocExporter exporter = new AdocExporter();
        exporter.writeToAdoc(
                file,
                RulesConceptsAndRelations.getInstance().getArchitectureRuleManager(),
                RulesConceptsAndRelations.getInstance().getConceptManager(),
                RulesConceptsAndRelations.getInstance().getRelationManager(),
                customQueries,
                freeTextQueries);
        this.projectFile = Optional.of(file);
    }

    public static String getDefaultProjectFileName() {
        return DEFAULT_PROJECT_FILE_NAME;
    }

    public Queue<FreeTextQuery> getFreeTextQueryQueue() {
        return freeTextQueryQueue;
    }

    public Queue<Query> getCustomQueryQueue() {
        return customQueryQueue;
    }
}