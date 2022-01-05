package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.triplet.TripletView;

public class AddTripletViewAfterButtonPressedEvent extends ComponentEvent<TripletView> {

    private static final long serialVersionUID = 8985171948706278913L;

    public AddTripletViewAfterButtonPressedEvent(TripletView source) {
        super(source, true);
    }
}
