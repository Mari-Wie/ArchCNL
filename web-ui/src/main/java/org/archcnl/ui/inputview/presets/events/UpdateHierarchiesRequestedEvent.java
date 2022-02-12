package org.archcnl.ui.inputview.presets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.presets.PresetsDialogPresenter;

public class UpdateHierarchiesRequestedEvent extends ComponentEvent<PresetsDialogPresenter> {

    /** */
    private static final long serialVersionUID = -373879922933805443L;

    public UpdateHierarchiesRequestedEvent(PresetsDialogPresenter source, boolean fromClient) {
        super(source, fromClient);
        // TODO Auto-generated constructor stub
    }
}
