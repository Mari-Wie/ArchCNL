package org.archcnl.ui.inputview.presets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.presets.PresetsDialogView;

public class ArchiecturalRulesSelectedEvent extends ComponentEvent<PresetsDialogView> {

    /** */
    private static final long serialVersionUID = -7316825880316303934L;

    public ArchiecturalRulesSelectedEvent(PresetsDialogView source, boolean fromClient) {
        super(source, fromClient);
    }
}
