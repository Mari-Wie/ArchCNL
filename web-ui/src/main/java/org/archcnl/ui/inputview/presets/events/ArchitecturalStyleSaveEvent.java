package org.archcnl.ui.inputview.presets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.ui.inputview.presets.ArchitecturalStyleForm;

public class ArchitecturalStyleSaveEvent extends ComponentEvent<ArchitecturalStyleForm> {

    private static final Logger LOG = LogManager.getLogger(ArchitecturalStyleSaveEvent.class);
    private static final long serialVersionUID = 1L;

    private ArchitecturalStyle style;

    public ArchitecturalStyleSaveEvent(
            ArchitecturalStyleForm architecturalStyleInputContainer,
            boolean fromClient,
            ArchitecturalStyle architecturalStyles) {
        super(architecturalStyleInputContainer, fromClient);
        this.style = architecturalStyles;
    }

    public void handleEvent() {
        switch (style) {
            case MICROSERVICE_ARCHITECTURE:
                getSource().mapUiComponentsToArchitecturalStyle(style);
                break;
            case LAYERED_ARCHITECTURE:
                ArchitecturalStyleSaveEvent.LOG.warn("{} is not implemented", style);
                break;
            default:
                break;
        }
    }
}
