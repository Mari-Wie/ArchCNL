package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;

public class WhenBlock extends VerticalLayout {
    /** */
    private static final long serialVersionUID = 1L;

    Label blockDestription;
    ArrayList<OrBlock> orBlocks;

    public WhenBlock() {
        blockDestription = new Label("when");
        add(blockDestription);
        createOrBlocks();
    }

    public void createOrBlocks() {
        orBlocks = new ArrayList<OrBlock>();
        orBlocks.add(new OrBlock());
        orBlocks.forEach(
                p -> {
                    p.addListener(PredicateSelectionEvent.class, e -> fireEvent(e));
                    p.addListener(
                            PredicateListUpdateRequest .class,
                            e -> {
                                System.out.println("Refireing Event in WhenBlock");
                                fireEvent(e);
                            });
                    p.addListener(
                            VariableSelectionEvent.class,
                            e -> {
                                System.out.println("Refireing Event in WhenBlock");
                                fireEvent(e);
                            });
                    add(p);
                });
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
