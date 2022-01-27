package org.archcnl.ui.menudialog;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import java.io.File;
import java.util.Optional;
import org.archcnl.ui.menudialog.events.QuickOutputViewAccessRequestedEvent;
import org.archcnl.ui.menudialog.events.RunToolchainRequestedEvent;

public class SelectDirectoryDialog extends Dialog implements FileSelectionDialog {

    private static final long serialVersionUID = 5511746171215269619L;
    private Button confirmButton;

    public SelectDirectoryDialog() {
        setDraggable(true);

        Text title = new Text("Select project directory to check for architecture violations");
        FileSelectionComponent fileSelectionComponent = new FileSelectionComponent(this, true);

        confirmButton =
                new Button(
                        "Run architecture check",
                        event -> {
                            Optional<File> file = fileSelectionComponent.getSelectedFile();
                            if (file.isEmpty()) {
                                // should not be possible as button should be disabled when this is
                                // the case
                                fileSelectionComponent.showErrorMessage(
                                        "Please select a directory.");
                            } else {
                                fireEvent(
                                        new RunToolchainRequestedEvent(
                                                this, true, file.get().getPath()));
                                close();
                            }
                        });
        confirmButton.setEnabled(false);
        Button cancelButton = new Button("Cancel", event -> close());
        Button quickOutputAccessButton =
                new Button(
                        "View queries without scan",
                        event -> {
                            fireEvent(new QuickOutputViewAccessRequestedEvent(this, true));
                            close();
                        });
        HorizontalLayout buttonRow =
                new HorizontalLayout(quickOutputAccessButton, confirmButton, cancelButton);
        buttonRow.setWidthFull();
        buttonRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        add(title, fileSelectionComponent, buttonRow);
    }

    @Override
    public void setConfirmButtonEnabled(boolean enabled) {
        confirmButton.setEnabled(enabled);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
