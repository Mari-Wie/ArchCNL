package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import java.util.stream.Collectors;

import org.archcnl.ui.input.newMappingEditor.events.PredicateListUpdateRequest;
import org.archcnl.ui.input.newMappingEditor.events.PredicateSelectionEvent;
import org.archcnl.ui.input.newMappingEditor.events.VariableListUpdateRequest;
import org.archcnl.ui.input.newMappingEditor.events.VariableSelectionEvent;
import org.archcnl.ui.output.events.AddWhereLayoutRequestEvent;
import org.archcnl.ui.output.events.RemoveWhereLayoutRequestEvent;

public class WhenBlock extends VerticalLayout {
    /** */
    private static final long serialVersionUID = 1L;

    Label blockDestription;
    OrderedListComponent blockHolder;

    public WhenBlock() {
        blockHolder = new OrderedListComponent();
        blockHolder.addListener(PredicateSelectionEvent.class, e -> fireEvent(e));
        blockHolder.addListener(
                PredicateListUpdateRequest.class,
                e -> {
                    System.out.println("Refireing Event in WhenBlock");
                    fireEvent(e);
                });
        blockHolder.addListener(
                VariableSelectionEvent.class,
                e -> {
                    System.out.println("Refireing Event in WhenBlock");
                    fireEvent(e);
                });
        blockDestription = new Label("when");
        add(blockDestription);
        add(blockHolder);
        createOrBlock(0);
    }

    public void removeRow(final Component component) {
        blockHolder.remove(component);
        if (blockHolder.getComponentCount() <= 0) {
            createOrBlock(0);
        }
    }

    public List<List<List<String>>> collect() {
        return blockHolder
                .getChildren()
                .filter(child -> child.getClass() == OrBlock.class)
                .map(OrBlock.class::cast)
                .map(OrBlock::collect)
                .collect(Collectors.toList());
    }

    public void createOrBlock(int position) {
        OrBlock newOrBlock = new OrBlock();
        newOrBlock.addListener(PredicateSelectionEvent.class, e -> fireEvent(e));
        newOrBlock.addListener(
                PredicateListUpdateRequest.class,
                e -> {
                    System.out.println("Refireing Event in WhenBlock");
                    fireEvent(e);
                });
        newOrBlock.addListener(
                VariableSelectionEvent.class,
                e -> {
                    System.out.println("Refireing Event in WhenBlock");
                    fireEvent(e);
                });

        newOrBlock.addListener(
                AddWhereLayoutRequestEvent.class,
                e -> createOrBlock(blockHolder.indexOf(e.getSource())));
        newOrBlock.addListener(RemoveWhereLayoutRequestEvent.class, e -> removeRow(e.getSource()));
        newOrBlock.addListener(VariableListUpdateRequest.class, e -> fireEvent(e));

        int newPosition = position;
        if (newPosition < blockHolder.getComponentCount()) {
            newPosition += 1;
        }
        blockHolder.addComponentAtIndex(newPosition, newOrBlock);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
