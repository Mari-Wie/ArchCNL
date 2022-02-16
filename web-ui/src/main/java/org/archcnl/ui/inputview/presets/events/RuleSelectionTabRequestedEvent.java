package org.archcnl.ui.inputview.presets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.presets.PresetsDialogView;

public class RuleSelectionTabRequestedEvent extends ComponentEvent<PresetsDialogView> {

    /** */
    private static final long serialVersionUID = -6484915312019460583L;

    public RuleSelectionTabRequestedEvent(PresetsDialogView source, boolean fromClient) {
        super(source, fromClient);
    }
}
