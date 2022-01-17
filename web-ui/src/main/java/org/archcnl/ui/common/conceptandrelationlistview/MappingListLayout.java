package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import org.archcnl.ui.common.conceptandrelationlistview.mappinglistlayout.MappingListEntry;
import org.archcnl.ui.common.conceptandrelationlistview.mappinglistlayout.MappingListEntryLayout;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RelationEditorRequestedEvent;

public class MappingListLayout extends TreeGrid<MappingListEntry> {

    private static final long serialVersionUID = 3L;

    public MappingListLayout(final List<MappingListEntry> entries) {
        super();
        setItems(entries, MappingListEntry::getChildren);
        addComponentHierarchyColumn(
                entry -> {
                    final MappingListEntryLayout entryLayout = new MappingListEntryLayout(entry);
                    entryLayout.addListener(ConceptEditorRequestedEvent.class, this::fireEvent);
                    entryLayout.addListener(RelationEditorRequestedEvent.class, this::fireEvent);

                    if (entry.isLeaf()) {
                        final DragSource<MappingListEntryLayout> dragSource =
                                DragSource.create(entryLayout);
                        dragSource.addDragStartListener(event -> {});
                        // TODO: Change this to the desired behavior
                        dragSource.setDragData(entry.getContent());
                    }
                    return entryLayout;
                });
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
