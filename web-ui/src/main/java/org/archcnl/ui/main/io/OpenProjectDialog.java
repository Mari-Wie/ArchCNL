package org.archcnl.ui.main.io;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.archcnl.domain.input.ProjectManager;
import org.archcnl.domain.input.io.ArchRulesFromAdocReader;
import org.archcnl.domain.input.io.ArchRulesImporter;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class OpenProjectDialog extends Dialog implements OpenSaveProjectDialog {

    private static final long serialVersionUID = 6550339926202761828L;
    private Button confirmButton;

    public OpenProjectDialog() {
        setDraggable(true);

        Text title = new Text("Select project file to open)");
        FileSelectionComponent fileSelectionComponent = new FileSelectionComponent(this);

        confirmButton =
                new Button(
                        "Open",
                        event -> {
                            ArchRulesImporter importer = new ArchRulesFromAdocReader();
                            Optional<File> file = fileSelectionComponent.getSelectedFile();
                            if (file.isEmpty()) {
                                // should not be possible as button should be disabled when this is
                                // the case
                                fileSelectionComponent.showErrorMessage("Please select a file.");
                            } else {
                                try {
                                    importer.readArchitectureRules(
                                            file.get(), RulesConceptsAndRelations.getInstance());
                                    ProjectManager.getInstance().setProjectFile(file.get());
                                    close();
                                } catch (IOException e) {
                                    fileSelectionComponent.showErrorMessage(
                                            "File could not be opened.");
                                }
                            }
                        });
        confirmButton.setEnabled(false);
        Button cancelButton = new Button("Cancel", event -> close());
        HorizontalLayout buttonRow = new HorizontalLayout(confirmButton, cancelButton);
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        add(title, fileSelectionComponent, buttonRow);
    }

    @Override
    public void setConfirmButtonEnabled(boolean enabled) {
        confirmButton.setEnabled(enabled);
    }
}
