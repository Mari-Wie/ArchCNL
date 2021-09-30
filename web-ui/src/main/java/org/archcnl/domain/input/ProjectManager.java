package org.archcnl.domain.input;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.archcnl.domain.input.io.ArchRulesExporter;
import org.archcnl.domain.input.io.ArchRulesFromAdocReader;
import org.archcnl.domain.input.io.ArchRulesImporter;
import org.archcnl.domain.input.io.ArchRulesToAdocWriter;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class ProjectManager {

    private static final ProjectManager INSTANCE = new ProjectManager();
    private static final String DEFAULT_PROJECT_FILE_NAME = "ArchCNL-architecture_rules.adoc";
    private PropertyChangeSupport propertyChangeSupport;
    private Optional<File> projectFile;

    private ProjectManager() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public static ProjectManager getInstance() {
        return INSTANCE;
    }

    public Optional<File> getProjectFile() {
        return projectFile;
    }

    private void setProjectFile(File projectFile) {
        propertyChangeSupport.firePropertyChange("projectFile", this.projectFile, projectFile);
        this.projectFile = Optional.of(projectFile);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void openProject(File file) throws IOException {
        ArchRulesImporter importer = new ArchRulesFromAdocReader();
        importer.readArchitectureRules(file, RulesConceptsAndRelations.getInstance());
        setProjectFile(file);
    }

    /**
     * Writes the project to the specified projectFile. Does nothing when projectFile is not set
     * This function must only be called when a project is opened using the
     * ProjectManager.openProject function
     *
     * @throws IOException when file cannot be written
     */
    public void saveProject() throws IOException {
        if (projectFile.isPresent()) {
            ArchRulesExporter exporter = new ArchRulesToAdocWriter();
            exporter.writeArchitectureRules(
                    projectFile.get(), RulesConceptsAndRelations.getInstance());
        }
    }

    public void saveProject(File file) throws IOException {
        ArchRulesExporter exporter = new ArchRulesToAdocWriter();
        exporter.writeArchitectureRules(file, RulesConceptsAndRelations.getInstance());
    }

    public static String getDefaultProjectFileName() {
        return DEFAULT_PROJECT_FILE_NAME;
    }
}
