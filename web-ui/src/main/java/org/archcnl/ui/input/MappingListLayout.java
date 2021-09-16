package org.archcnl.ui.input;

import com.vaadin.flow.component.treegrid.TreeGrid;
import java.util.List;

public class MappingListLayout<T> extends TreeGrid<MappingListEntry<T>> {

    private static final long serialVersionUID = 3L;

    public MappingListLayout(List<MappingListEntry<T>> entries) {
        super();
        setItems(entries, MappingListEntry::getHierarchicalChildren);
        addComponentHierarchyColumn(
                entry -> {
                    MappingListEntryLayout<T> entryLayout = new MappingListEntryLayout<T>(entry);
                    return entryLayout;
                });
    }
}
