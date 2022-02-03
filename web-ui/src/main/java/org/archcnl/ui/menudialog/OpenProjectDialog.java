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
import java.io.IOException;
import java.util.Optional;
import java.util.Queue;
import org.archcnl.domain.common.ProjectManager;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.ui.menudialog.events.ProjectOpenedEvent;
import org.archcnl.ui.menudialog.events.ShowCustomQueryRequestedEvent;
import org.archcnl.ui.menudialog.events.ShowFreeTextQueryRequestedEvent;

public class OpenProjectDialog extends Dialog implements FileSelectionDialog {

    private static final long serialVersionUID = 6550339926202761828L;
    private Button confirmButton;

    public OpenProjectDialog(ProjectManager projectManager) {
        setDraggable(true);

        Text title = new Text("Select project file to open");
        FileSelectionComponent fileSelectionComponent = new FileSelectionComponent(this, false);

        confirmButton =
                new Button(
                        "Open",
                        event -> {
                            Optional<File> file = fileSelectionComponent.getSelectedFile();
                            if (file.isEmpty()) {
                                // should not be possible as button should be disabled when this is
                                // the case
                                fileSelectionComponent.showErrorMessage("Please select a file.");
                            } else {
                                try {
                                    projectManager.openProject(file.get());
                                    fireShowQueryEvents(
                                            projectManager.getFreeTextQueryQueue(),
                                            projectManager.getCustomQueryQueue());
                                    fireEvent(new ProjectOpenedEvent(this, true));
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

    private void fireShowQueryEvents(
            Queue<FreeTextQuery> freeTextQueries, Queue<Query> customQueries) {
        boolean defaultQuery = true;
        while (!freeTextQueries.isEmpty()) {
            fireEvent(
                    new ShowFreeTextQueryRequestedEvent(
                            this, false, freeTextQueries.poll(), defaultQuery));
            defaultQuery = false;
        }
        defaultQuery = true;
        while (!customQueries.isEmpty()) {
            fireEvent(
                    new ShowCustomQueryRequestedEvent(
                            this, false, customQueries.poll(), defaultQuery));
            defaultQuery = false;
        }
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
