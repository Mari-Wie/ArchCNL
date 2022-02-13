package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.events.GridUpdateRequestedEvent;
import org.archcnl.ui.events.HierarchyMoveRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;

public class HierarchyView<T extends HierarchyObject> extends RulesOrMappingEditorView {
    private TreeGrid<HierarchyNode<T>> treeGrid;
    List<HierarchyNode<T>> roots;
    private HierarchyNode<T> draggedItem;

    public HierarchyView() {
        setClassName("hierarchy");
        roots = new ArrayList<HierarchyNode<T>>();
        treeGrid = new TreeGrid<HierarchyNode<T>>();
        treeGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
        treeGrid.setRowsDraggable(true);
        treeGrid.addComponentHierarchyColumn(
                node -> {
                    return createNewHierarchyEntry(node);
                });
        setUpDragAndDrop();
        add(treeGrid);
    }

    public HierarchyEntryLayout createNewHierarchyEntry(HierarchyNode node) {
        HierarchyEntryLayout<T> newLayout = new HierarchyEntryLayout<T>(node);
        return newLayout;
    }

    public void addSection(String sectionName) {
        roots.add(new HierarchyNode<T>(sectionName));
    }

    public void setRoots(List<HierarchyNode<T>> l) {
        roots = l;
    }

    public void clearRoots() {
        roots.clear();
    }

    public void addRoot(HierarchyNode<T> newRoot) {
        roots.add(newRoot);
    }

    public void update() {
        treeGrid.setItems(roots, HierarchyNode::getChildren);
    }

    @Override
    public void onAttach(AttachEvent attachEvent) {
        requestGridUpdate();
    }

    public void requestGridUpdate() {
        fireEvent(new GridUpdateRequestedEvent(this, true));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    void getData() {
        // Collection<Foo> sourceItems = ((TreeDataProvider<Foo>)
        // fooTreeGrid.getDataProvider()).getTreeData().getRootItems();
    }

    void setUpDragAndDrop() {
        // Modifying the data view requires a mutable collection

        treeGrid.addDragStartListener(e -> draggedItem = e.getDraggedItems().get(0));

        treeGrid.addDropListener(
                e -> {
                    HierarchyNode<T> targetNode = e.getDropTargetItem().orElse(null);
                    GridDropLocation dropLocation = e.getDropLocation();

                    boolean personWasDroppedOntoItself = draggedItem.equals(targetNode);

                    if (targetNode == null || personWasDroppedOntoItself) return;

                    if (dropLocation == GridDropLocation.ON_TOP) {
                        fireEvent(
                                new HierarchyMoveRequestedEvent(
                                        this, false, draggedItem, targetNode, dropLocation));
                    } else {
                    }
                    requestGridUpdate();
                });

        treeGrid.addDragEndListener(e -> draggedItem = null);
    }
}
