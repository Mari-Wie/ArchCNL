package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteHierarchyObjectRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.GridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.HierarchySwapRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.NodeAddRequestedEvent;

public class HierarchyView<T extends HierarchyObject> extends VerticalLayout {

    private static final long serialVersionUID = 4353502586813149848L;
    private TreeGrid<HierarchyNode<T>> treeGrid;
    private List<HierarchyNode<T>> roots;
    private List<HierarchyNode<T>> expandedNodes;
    private HierarchyNode<T> draggedItem;
    // filter by name
    private TextField searchField = new TextField();
    private HorizontalLayout searchBar = new HorizontalLayout();
    private Button searchButton;
    private Button cancelButton;
    protected HorizontalLayout footer = new HorizontalLayout();
    protected Component addNodeElement;
    protected Button addNode = new Button("No LABEL");

    public HierarchyView() {
        setClassName("hierarchy");
        footer = new HorizontalLayout();
        roots = new ArrayList<>();
        expandedNodes = new ArrayList<>(roots);
        treeGrid = new TreeGrid<>();
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

        searchField.setPlaceholder("Search");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.setClearButtonVisible(true);
        searchField.setWidth("80%");

        searchField.addValueChangeListener(
                ev -> {
                    if (ev.getValue().isEmpty()) {
                        treeGrid.expand(expandedNodes);
                    }
                    ((TreeDataProvider<HierarchyNode<T>>) treeGrid.getDataProvider())
                            .setFilter(
                                    HierarchyNode::getName,
                                    t -> {
                                        String cleanedEv =
                                                ev.getSource().getValue().replaceAll("\\s+", "");
                                        for (String val : cleanedEv.split("\\|")) {
                                            if (t.contains(val)) {
                                                return true;
                                            }
                                        }
                                        return false;
                                    });
                });
        searchButton = new Button(new Icon(VaadinIcon.SEARCH), click -> replace(footer, searchBar));
        cancelButton =
                new Button(
                        new Icon(VaadinIcon.CLOSE),
                        click -> {
                            searchField.setValue("");
                            replace(searchBar, footer);
                        });
        searchBar.add(searchField, cancelButton);
    }

    public HorizontalLayout createEditorButton(
            String buttonLabel, ComponentEventListener<ClickEvent<Button>> clickListener) {

        addNode = new Button(buttonLabel, clickListener);
        addNodeElement = addNode;
        footer.add(addNodeElement);
        return footer;
    }

    public HierarchyEntryLayout<T> createNewHierarchyEntry(HierarchyNode<T> node) {
        HierarchyEntryLayout<T> newLayout;
        HierarchyEntryLayoutFactory<T> factory = new HierarchyEntryLayoutFactory<>();
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
        newTextField.addBlurListener(e -> footer.replace(newTextField, addNode));

        footer.replace(addNodeElement, newTextField);
    }

    public void addSection(String sectionName) {
        roots.add(new HierarchyNode<>(sectionName));
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
        treeGrid.expand(roots);
        footer.add(searchButton);
    }

    public void requestGridUpdate() {
        fireEvent(new GridUpdateRequestedEvent(this, true));
    }

    @Override
    public <E extends ComponentEvent<?>> Registration addListener(
            final Class<E> eventType, final ComponentEventListener<E> listener) {
        return getEventBus().addListener(eventType, listener);
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
                                new HierarchySwapRequestedEvent<T>(
                                        this, false, draggedItem, targetNode, dropLocation));
                    } else {
                        // TODO: Notify the user
                    }
                    requestGridUpdate();
                });

        treeGrid.addDragEndListener(e -> draggedItem = null);
    }
}
