package org.archcnl.ui.inputview.presets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.presets.PresetsDialogView;

public class ValidateArchitecturalStyleFormEvent extends ComponentEvent<PresetsDialogView> {

    private static final long serialVersionUID = 1L;

    public ValidateArchitecturalStyleFormEvent(PresetsDialogView source, boolean fromClient) {
        super(source, fromClient);
    }
}
