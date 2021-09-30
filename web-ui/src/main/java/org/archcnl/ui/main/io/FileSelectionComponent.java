package org.archcnl.ui.main.io;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Optional;
import org.vaadin.filesystemdataprovider.FileSelect;

public class FileSelectionComponent extends VerticalLayout {

    private static final long serialVersionUID = -1340341590751453530L;
    private PropertyChangeSupport propertyChangeSupport;
    private final File defaultRootFile;
    private FileSelect fileSelect;
    private Optional<File> selectedFile = Optional.empty();
    private TextField pathField;

    public FileSelectionComponent() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        defaultRootFile = Paths.get(System.getProperty("user.home")).getRoot().toFile();
        fileSelect = createFileSelect(defaultRootFile);

        pathField = new TextField("Enter a path to change the directory.");
        pathField.setPlaceholder(defaultRootFile.getAbsolutePath());
        pathField.setClearButtonVisible(true);
        pathField.setWidthFull();
        pathField.addValueChangeListener(
                event -> {
                    String value = event.getValue();
                    if (value.equals(pathField.getEmptyValue())) {
                        value = defaultRootFile.getAbsolutePath();
                    }
                    try {
                        File file = Paths.get(value).toFile();
                        if (!file.isDirectory()) {
                            // Get parent directory
                            file = file.getParentFile();
                        }
                        FileSelect newFileSelect = createFileSelect(file);
                        replace(fileSelect, newFileSelect);
                        fileSelect = newFileSelect;
                        pathField.setInvalid(false);
                    } catch (InvalidPathException | NullPointerException e) {
                        pathField.setErrorMessage("Entered value is not a path.");
                        pathField.setInvalid(true);
                    }
                });

        add(pathField, fileSelect);
    }

    private FileSelect createFileSelect(File rootFile) {
        FileSelect newFileSelect = new FileSelect(rootFile, "adoc");
        newFileSelect
                .getChildren()
                .filter(TreeGrid.class::isInstance)
                .map(TreeGrid.class::cast)
                .forEach(treeGrid -> treeGrid.expand(treeGrid.getTreeData().getRootItems()));
        newFileSelect.addValueChangeListener(
                event -> {
                    Optional<File> file = newFileSelect.getOptionalValue();
                    if (file.isPresent() && file.get().isFile()) {
                        selectedFile = file;
                        hideErrorMessage();
                    } else {
                        selectedFile = Optional.empty();
                        showErrorMessage("Please select a file.");
                    }
                });
        newFileSelect.setWidth("500px");
        newFileSelect.setHeight("500px");
        return newFileSelect;
    }

    private void setFileSelectInvalid(boolean invalid) {
        propertyChangeSupport.firePropertyChange(
                "isPathFieldInvalid", pathField.isInvalid(), invalid);
        fileSelect.setInvalid(invalid);
    }

    public void showErrorMessage(String errorMessage) {
        fileSelect.setErrorMessage(errorMessage);
        setFileSelectInvalid(true);
    }

    public void hideErrorMessage() {
        setFileSelectInvalid(false);
    }

    public Optional<File> getSelectedFile() {
        return selectedFile;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
