package org.archcnl.ui.inputview.presets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.ui.inputview.presets.PresetsDialogView;

public class ValidateArchitecturalStyleFormEvent extends ComponentEvent<PresetsDialogView> {

    private static final Logger LOG =
            LogManager.getLogger(ValidateArchitecturalStyleFormEvent.class);
    private static final long serialVersionUID = 1L;

    private ArchitecturalStyle style;

    public ValidateArchitecturalStyleFormEvent(PresetsDialogView source, boolean fromClient) {
        super(source, fromClient);
    }

    //    public void handleEvent() {
    //        switch (style) {
    //            case MICROSERVICE_ARCHITECTURE:
    //                getSource().mapUiComponentsToArchitecturalStyle(style);
    //                break;
    //            case LAYERED_ARCHITECTURE:
    //                new Notification();
    //                getSource().add(Notification.show("Not yet Implemented"));
    //                ArchitecturalStyleSaveEvent.LOG.warn("{} is not implemented", style);
    //                break;
    //            default:
    //                break;
    //        }
    //    }
}
