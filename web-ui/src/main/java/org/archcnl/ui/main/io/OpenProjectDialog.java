package org.archcnl.ui.main.io;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.io.IOException;
import org.archcnl.domain.input.io.ArchRulesFromAdocReader;
import org.archcnl.domain.input.io.ArchRulesImporter;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;

public class OpenProjectDialog extends Dialog {

    private static final long serialVersionUID = 6550339926202761828L;

    public OpenProjectDialog() {
        setDraggable(true);

        Text title = new Text("Select project file (.adoc)");
        Label errorField = new Label("");
        errorField.getStyle().set("color", "red");
        errorField.setVisible(false);

        FileSelectionComponent fileSelectionComponent = new FileSelectionComponent();

        Button confirmButton =
                new Button(
                        "OK",
                        event -> {
                            ArchRulesImporter importer = new ArchRulesFromAdocReader();
                            try {
                                importer.readArchitectureRules(
                                        null, RulesConceptsAndRelations.getInstance());
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

        add(title, fileSelectionComponent, errorField, buttonRow);
    }
}
