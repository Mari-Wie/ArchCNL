package org.archcnl.domain.input;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Optional;

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

    public void setProjectFile(File projectFile) {
        propertyChangeSupport.firePropertyChange("projectFile", this.projectFile, projectFile);
        this.projectFile = Optional.of(projectFile);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
