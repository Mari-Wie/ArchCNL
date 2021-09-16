package org.archcnl.ui.input;

import com.vaadin.flow.component.treegrid.TreeGrid;
import java.util.List;

public class MappingListLayout extends TreeGrid<MappingListEntry> {

    private static final long serialVersionUID = 3L;

    public MappingListLayout(List<MappingListEntry> entries) {
        super();
        setItems(entries, MappingListEntry::getChildren);
        addComponentHierarchyColumn(
                entry -> {
                    MappingListEntryLayout entryLayout = new MappingListEntryLayout(entry);
                    return entryLayout;
                });
    }
}
