package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

public class OrBlock extends VerticalLayout {
    /** */
    private static final long serialVersionUID = 1L;

    public OrBlock() {
        setPadding(false);
        getStyle().set("border", "1px solid black");
        add(new Label("OR-Block: All rows in this block are AND connected"));
        createTriplet();
    }
    
    private void createTriplet(){
       ComplexTriplet tripletList = new ComplexTriplet();
        tripletList.addListener(PredicateSelectionEvent.class, e -> fireEvent(e));
        tripletList.addListener(VariableSelectionEvent.class, e -> {System.out.println("Refireing from Orblock");fireEvent(e);});
        add(tripletList);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
