package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.TripletView;

public class TripletViewDeleteButtonPressedEvent extends ComponentEvent<TripletView> {

    private static final long serialVersionUID = -9084190144665168189L;

    public TripletViewDeleteButtonPressedEvent(TripletView source) {
        super(source, true);
    }
}
