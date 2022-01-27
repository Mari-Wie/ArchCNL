package org.archcnl.ui.menudialog.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.menudialog.SelectDirectoryDialog;

public class RunToolchainRequestedEvent extends ComponentEvent<SelectDirectoryDialog> {

    private static final long serialVersionUID = -8545127073198719076L;
    private String selectedPath;

    public RunToolchainRequestedEvent(
            SelectDirectoryDialog source, boolean fromClient, String selectedPath) {
        super(source, fromClient);
        this.selectedPath = selectedPath;
    }

    public String getSelectedPath() {
        return selectedPath;
    }
}
