package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteHierarchyObjectRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.GridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.HierarchySwapRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.NodeAddRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;

public class HierarchyView<T extends HierarchyObject> extends RulesOrMappingEditorView {
    private TreeGrid<HierarchyNode<T>> treeGrid;
    List<HierarchyNode<T>> roots;
    List<HierarchyNode<T>> expandedNodes;
    private HierarchyNode<T> draggedItem;

    public HierarchyView() {
        setClassName("hierarchy");
        roots = new ArrayList<HierarchyNode<T>>();
        expandedNodes = new ArrayList<HierarchyNode<T>>(roots);
        treeGrid = new TreeGrid<HierarchyNode<T>>();
        treeGrid.setDropMode(GridDropMode.ON_TOP_OR_BETWEEN);
        treeGrid.setRowsDraggable(true);
        treeGrid.addExpandListener(e -> expandedNodes.addAll(e.getItems()));
        treeGrid.addCollapseListener(e -> expandedNodes.removeAll(e.getItems()));
        treeGrid.addComponentHierarchyColumn(
                node -> {
                    return createNewHierarchyEntry(node);
                });
        setUpDragAndDrop();
        add(treeGrid);
        add(footer);
    }

    public HierarchyEntryLayout createNewHierarchyEntry(HierarchyNode node) {
        HierarchyEntryLayout<T> newLayout;
        HierarchyEntryLayoutFactory factory = new HierarchyEntryLayoutFactory<T>();
        if (node.isRemoveable()) {
            newLayout = factory.createRemovable(node);
            newLayout.addListener(DeleteHierarchyObjectRequestedEvent.class, this::fireEvent);
        } else {
            newLayout = factory.createStatic(node);
        }
        return newLayout;
    }

    public void addTextField() {
        TextField newTextField = new TextField();
        newTextField.focus();
        newTextField.addKeyPressListener(
                Key.ENTER,
                event -> {
                    if (!newTextField.getValue().isEmpty()) {
                        footer.replace(newTextField, addNode);
                        fireEvent(new NodeAddRequestedEvent(this, newTextField.getValue(), true));
                    }
                });
        newTextField.addBlurListener(
                e -> {
                    footer.replace(newTextField, addNode);
                });

        footer.replace(addNodeElement, newTextField);
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
        treeGrid.expand(expandedNodes);
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

    private void getData() {
        // Collection<Foo> sourceItems = ((TreeDataProvider<Foo>)
        // fooTreeGrid.getDataProvider()).getTreeData().getRootItems();
    }

    private void setUpDragAndDrop() {
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
                                new HierarchySwapRequestedEvent(
                                    this, false, draggedItem, targetNode, dropLocation));
                    } else {
                    }
                    requestGridUpdate();
                });

        treeGrid.addDragEndListener(e -> draggedItem = null);
    }
}
