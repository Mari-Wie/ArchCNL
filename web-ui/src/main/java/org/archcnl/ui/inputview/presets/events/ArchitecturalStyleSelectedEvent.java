package org.archcnl.ui.inputview.presets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.ui.inputview.presets.ArchitectureStyleSelection;

public class ArchitecturalStyleSelectedEvent extends ComponentEvent<ArchitectureStyleSelection> {

    /** */
    private static final long serialVersionUID = -3410874386784534179L;

    private ArchitecturalStyle style;

    public ArchitecturalStyleSelectedEvent(
            ArchitectureStyleSelection source,
            boolean fromClient,
            ArchitecturalStyle architecturalStyle) {
        super(source, fromClient);
        this.style = architecturalStyle;
    }

    public ArchitecturalStyle getStyle() {
        return style;
    }
}
