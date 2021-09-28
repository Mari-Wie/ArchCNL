package org.archcnl.ui.main;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.input.io.ArchRulesFromAdocReader;
import org.archcnl.domain.input.io.ArchRulesImporter;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class OpenProjectDialog extends Dialog {

    private static final long serialVersionUID = 6550339926202761828L;

    public OpenProjectDialog() {
        setDraggable(true);
        File projectFile;
        try {
            projectFile = File.createTempFile("ArchCNL-project-", ".adoc");
            projectFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException("Creation of temporary file failed unexpectedly");
        }

        Text title = new Text("Select project file (.adoc)");
        Label errorField = new Label("");
        errorField.getStyle().set("color", "red");
        errorField.setVisible(false);

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".adoc");
        upload.setMaxFiles(1);

        Button confirmButton =
                new Button(
                        "OK",
                        event -> {
                            ArchRulesImporter importer = new ArchRulesFromAdocReader();
                            try {
                                importer.readArchitectureRules(
                                        projectFile, RulesConceptsAndRelations.getInstance());
                                close();
                            } catch (IOException e) {
                                errorField.setText("Reading of temporary file failed unexpecedly.");
                                errorField.setVisible(true);
                            }
                        });
        Button cancelButton = new Button("Cancel", event -> close());
        confirmButton.setVisible(false);
        HorizontalLayout buttonRow = new HorizontalLayout(confirmButton, cancelButton);
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        upload.addSucceededListener(
                event -> {
                    try {
                        FileUtils.copyInputStreamToFile(buffer.getInputStream(), projectFile);
                        confirmButton.setVisible(true);
                        errorField.setVisible(false);
                    } catch (IOException e) {
                        errorField.setText("Writing of temporary file failed unexpecedly.");
                        confirmButton.setVisible(false);
                        errorField.setVisible(true);
                    }
                });
        upload.addFileRejectedListener(
                event -> {
                    errorField.setText(event.getErrorMessage());
                    confirmButton.setVisible(false);
                    errorField.setVisible(true);
                });
        upload.getElement()
                .addEventListener(
                        "file-remove",
                        event -> {
                            confirmButton.setVisible(false);
                            errorField.setVisible(false);
                        });

        add(title, upload, errorField, buttonRow);
    }
}
