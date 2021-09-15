package org.archcnl.ui.input;

import java.util.List;

import com.vaadin.flow.component.treegrid.TreeGrid;

public class TreeGridListLayout<T> extends TreeGrid<TreeGridListEntry<T>> {
	
    private static final long serialVersionUID = 3L;
	
	public TreeGridListLayout(List<TreeGridListEntry<T>> entries) {
		super();
		setItems(entries, TreeGridListEntry::getHierarchicalChildren);
		addComponentHierarchyColumn(
                entry -> {
                    TreeGridListEntryLayout<T> entryLayout = new TreeGridListEntryLayout<T>(entry);
                    return entryLayout;
                });
	}

}
