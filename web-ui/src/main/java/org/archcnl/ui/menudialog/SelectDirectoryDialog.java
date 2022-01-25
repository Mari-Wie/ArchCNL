package org.archcnl.ui.menudialog;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import java.io.File;
import java.util.Optional;

import org.archcnl.domain.common.ArchitectureCheck;

public class SelectDirectoryDialog extends Dialog implements FileSelectionDialog {

    private static final long serialVersionUID = 6550339926202761828L;
    private Button confirmButton;
    private String selectedPath;

    public SelectDirectoryDialog(ArchitectureCheck check) {
        setDraggable(true);

        Text title = new Text("Select project directory to check for architecture violations");
        FileSelectionComponent fileSelectionComponent = new FileSelectionComponent(this, true);

        confirmButton =
                new Button(
                        "Open",
                        event -> {
                            Optional<File> file = fileSelectionComponent.getSelectedFile();
                            if (file.isEmpty()) {
                                // should not be possible as button should be disabled when this is
                                // the case
                                fileSelectionComponent.showErrorMessage("Please select a directory.");
                            } else {
                            	selectedPath = file.get().getPath();
								close();
                            }
                        });
        confirmButton.setEnabled(false);
        Button cancelButton = new Button("Cancel", event -> close());
        HorizontalLayout buttonRow = new HorizontalLayout(confirmButton, cancelButton);
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        add(title, fileSelectionComponent, buttonRow);
    }

    public String getSelectedPath() {
		return selectedPath;
	}

	@Override
    public void setConfirmButtonEnabled(boolean enabled) {
        confirmButton.setEnabled(enabled);
    }
}
