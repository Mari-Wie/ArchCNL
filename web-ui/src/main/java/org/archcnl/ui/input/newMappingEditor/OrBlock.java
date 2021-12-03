package org.archcnl.ui.input.newMappingEditor;

import java.util.List;
import java.util.stream.Collectors;

import org.archcnl.ui.output.events.AddWhereLayoutRequestEvent;
import org.archcnl.ui.output.events.RemoveWhereLayoutRequestEvent;

import com.vaadin.flow.component.Component;
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
        createTriplet(0);
    }

    private void createTriplet(int position) {
        ComplexTriplet triplet = new ComplexTriplet();
        triplet.addListener(PredicateSelectionEvent.class, e -> fireEvent(e));
        triplet.addListener( PredicateListUpdateRequest.class, e -> { System.out.println("Refireing from Orblock"); fireEvent(e); });
        triplet.addListener( VariableSelectionEvent.class, e -> { System.out.println("Refireing from Orblock"); fireEvent(e); });
       triplet.addListener( AddWhereLayoutRequestEvent.class, e -> createTriplet(indexOf(e.getSource())));
       triplet.addListener(RemoveWhereLayoutRequestEvent.class, e -> removeRow(e.getSource()));

        int newPosition = position;
        if (newPosition < getComponentCount()) {
            newPosition += 1;
        }
        addComponentAtIndex(newPosition, triplet);
    }
  public void removeRow(final Component component) {
        remove(component);
        if (collect().size() <= 0) {
            createTriplet(0);
        }
    }

    public void clear() {
        removeAll();
        createTriplet(0);
    }

    public List<List<String>> collect() {
        return getChildren()
                .filter(child -> child.getClass() == ComplexTriplet.class)
                .map(ComplexTriplet.class::cast)
                .map(ComplexTriplet::gather)
                .collect(Collectors.toList());
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}