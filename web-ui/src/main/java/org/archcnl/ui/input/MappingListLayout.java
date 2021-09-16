package org.archcnl.ui.input;

import com.vaadin.flow.component.dnd.DragSource;
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
                    
                    if(entry.isLeaf()) {
                    	DragSource<MappingListEntryLayout> dragSource = DragSource.create(entryLayout);
                    	dragSource.addDragStartListener(event -> {
                    	});
                    	// TODO: Change this to the desired behavior
                    	dragSource.setDragData(entry.getContent().toString());
                    	dragSource.addDragEndListener(event -> {
            			System.out.println(dragSource.getDragData());
            		});
                    }
            		
                    return entryLayout;
                });
    }
}
