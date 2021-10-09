package org.archcnl.ui.main.io;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;
import org.vaadin.filesystemdataprovider.FileSelect;

public class FileSelectionComponent extends VerticalLayout {

    private static final long serialVersionUID = -1340341590751453530L;
    private final File defaultRootFile;
    private FileSelect fileSelect;
    private Optional<File> selectedFile = Optional.empty();
    private TextField pathField;
    private FileSelectionDialog dialog;
    private boolean selectDirectory;
    private Label fileSelectErrorLabel;

    public FileSelectionComponent(FileSelectionDialog dialog, boolean selectDirectory) {
        this.dialog = dialog;
        this.selectDirectory = selectDirectory;
        defaultRootFile = Paths.get(System.getProperty("user.home")).getRoot().toFile();
        fileSelect = createFileSelect(defaultRootFile);

        pathField = new TextField("Enter a path to change the directory");
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

        fileSelectErrorLabel = new Label();
        fileSelectErrorLabel.setVisible(false);
        fileSelectErrorLabel.getStyle().set("color", "red");

        add(pathField, fileSelect, fileSelectErrorLabel);
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
                    if (selectDirectory) {
                        handleDirectoryChange(file);
                    } else {
                        if (file.isPresent() && file.get().isFile()) {
                            selectedFile = file;
                            dialog.setConfirmButtonEnabled(true);
                            fileSelectErrorLabel.setVisible(false);
                        } else {
                            selectedFile = Optional.empty();
                            showErrorMessage("Please select a file.");
                        }
                    }
                });

        // ExpandListener is necessary as the ValueChangeListener is only activated when either a
        // file
        // or an empty directory is selected
        if (selectDirectory) {
            for (Component child : newFileSelect.getChildren().collect(Collectors.toList())) {
                if (child instanceof TreeGrid) {
                    TreeGrid<File> grid = (TreeGrid<File>) child;
                    grid.addExpandListener(
                            event -> {
                                File file = event.getItems().iterator().next();
                                handleDirectoryChange(Optional.of(file));
                            });
                }
            }
        }

        newFileSelect.setWidth("500px");
        newFileSelect.setHeight("500px");
        return newFileSelect;
    }

    public void showErrorMessage(String errorMessage) {
        fileSelectErrorLabel.setText(errorMessage);
        dialog.setConfirmButtonEnabled(false);
        fileSelectErrorLabel.setVisible(true);
    }

    public Optional<File> getSelectedFile() {
        return selectedFile;
    }

    private void handleDirectoryChange(Optional<File> file) {
        if (file.isPresent() && file.get().isDirectory()) {
            selectedFile = file;
            dialog.setConfirmButtonEnabled(true);
            fileSelectErrorLabel.setVisible(false);
        } else {
            selectedFile = Optional.empty();
            showErrorMessage("Please select a directory.");
        }
    }
}
