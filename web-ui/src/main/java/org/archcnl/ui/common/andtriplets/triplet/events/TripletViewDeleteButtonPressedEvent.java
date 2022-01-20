package org.archcnl.ui.common.andtriplets.triplet.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.common.andtriplets.triplet.TripletView;

public class TripletViewDeleteButtonPressedEvent extends ComponentEvent<TripletView> {

    private static final long serialVersionUID = -9084190144665168189L;

    public TripletViewDeleteButtonPressedEvent(TripletView source) {
        super(source, true);
    }
}
