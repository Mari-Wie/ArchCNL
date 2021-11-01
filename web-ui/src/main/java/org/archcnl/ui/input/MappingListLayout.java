package org.archcnl.ui.input;

import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;

import java.util.List;

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
        
        addSelectionListener(new SelectionListener<Grid<MappingListEntry>, MappingListEntry>() {            
            @Override
            public void selectionChange(SelectionEvent<Grid<MappingListEntry>, MappingListEntry> event) {                
                String tooltipString = event.getFirstSelectedItem().get().getDescription();
                getElement().setAttribute("title", tooltipString);
            }
        });      

        }
    }
    


