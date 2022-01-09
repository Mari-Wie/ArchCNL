package org.archcnl.ui.inputview.rulesormappingeditorview;

import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.treegrid.TreeGrid;
import java.util.List;
import org.archcnl.ui.inputview.InputContract;
import org.archcnl.ui.inputview.conceptandrelationlistview.mappinglistlayout.MappingListEntry;
import org.archcnl.ui.inputview.conceptandrelationlistview.mappinglistlayout.MappingListEntryLayout;

public class MappingListLayout extends TreeGrid<MappingListEntry> {

    private static final long serialVersionUID = 3L;

    public MappingListLayout(List<MappingListEntry> entries, InputContract.Remote inputRemote) {
        super();
        setItems(entries, MappingListEntry::getChildren);
        addComponentHierarchyColumn(
                entry -> {
                    MappingListEntryLayout entryLayout =
                            new MappingListEntryLayout(entry, inputRemote);

                    if (entry.isLeaf()) {
                        DragSource<MappingListEntryLayout> dragSource =
                                DragSource.create(entryLayout);
                        dragSource.addDragStartListener(event -> {});
                        // TODO: Change this to the desired behavior
                        dragSource.setDragData(entry.getContent());
                    }
                    return entryLayout;
                });
    }
}
