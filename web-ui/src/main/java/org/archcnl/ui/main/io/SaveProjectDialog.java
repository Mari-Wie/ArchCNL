package org.archcnl.ui.main.io;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.archcnl.domain.input.ProjectManager;

public class SaveProjectDialog extends Dialog implements OpenSaveProjectDialog {

    private static final long serialVersionUID = 6538573408820948553L;
    private Button confirmButton;

    public SaveProjectDialog() {
        setDraggable(true);

        Text title = new Text("Save project to another location)");
        FileSelectionComponent fileSelectionComponent = new FileSelectionComponent(this, true);

        TextField fileNameField = new TextField("Enter a name for the .adoc file");
        fileNameField.setPlaceholder(ProjectManager.getDefaultProjectFileName());
        fileNameField.setClearButtonVisible(true);
        fileNameField.setWidthFull();
        fileNameField.addValueChangeListener(
                event -> {
                    String value = event.getValue();
                    if (!value.equals(fileNameField.getEmptyValue())
                            && !value.matches("[-_A-Za-z0-9]+(\\.adoc)?")) {
                        setConfirmButtonEnabled(false);
                        if (!value.endsWith(".adoc")) {
                            fileNameField.setErrorMessage("The file extension must be \".adoc\"");
                            fileNameField.setInvalid(true);
                        } else {
                            fileNameField.setErrorMessage("Invalid file name");
                            fileNameField.setInvalid(true);
                        }
                    } else {
                        setConfirmButtonEnabled(true);
                        fileNameField.setInvalid(false);
                    }
                });

        confirmButton =
                new Button(
                        "Save",
                        event -> {
                            Optional<File> file = fileSelectionComponent.getSelectedFile();
                            if (file.isEmpty()) {
                                fileSelectionComponent.showErrorMessage(
                                        "Please select a directory.");
                            } else {
                                try {
                                    String fileName = fileNameField.getValue();
                                    if (fileName.equals(fileNameField.getEmptyValue())) {
                                        fileName = ProjectManager.getDefaultProjectFileName();
                                    } else if (!fileName.endsWith(".adoc")) {
                                        fileName = fileName + ".adoc";
                                    }
                                    Path path = Paths.get(file.get().getAbsolutePath(), fileName);
                                    ProjectManager.getInstance().saveProject(path.toFile());
                                    close();
                                } catch (IOException e) {
                                    fileSelectionComponent.showErrorMessage(
                                            "File could not be written.");
                                }
                            }
                        });
        confirmButton.setEnabled(false);
        Button cancelButton = new Button("Cancel", event -> close());
        HorizontalLayout buttonRow = new HorizontalLayout(confirmButton, cancelButton);
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        add(title, fileSelectionComponent, fileNameField, buttonRow);
    }

    @Override
    public void setConfirmButtonEnabled(boolean enabled) {
        confirmButton.setEnabled(enabled);
    }
}
