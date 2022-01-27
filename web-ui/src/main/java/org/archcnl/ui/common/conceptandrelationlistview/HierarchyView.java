package org.archcnl.ui.common.conceptandrelationlistview;
import com.vaadin.flow.component.treegrid.TreeGrid;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.ui.events.GridUpdateRequestedEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import java.util.List;

public class HierarchyView<T> extends VerticalLayout {
    private TreeGrid<HierarchyNode<T>> treeGrid;
    List<HierarchyNode<T>> roots;
    public HierarchyView(){
        getStyle().set("border", "1px solid black");
        roots = new ArrayList<HierarchyNode<T>>();
        treeGrid = new TreeGrid<>();
        treeGrid.addHierarchyColumn(HierarchyNode::toString);
        add(treeGrid);
    }

    public void addRoot(HierarchyNode<T> newRoot){
        roots.add(newRoot);
    }
    public void update(){
        treeGrid.setItems(roots, HierarchyNode::getChildren);
    }

    @Override
    public void onAttach(AttachEvent attachEvent){
        System.out.println("onAttach event fired in HierarchyView");
        requestGridUpdate();
    }

    public void requestGridUpdate(){
        fireEvent(new GridUpdateRequestedEvent(this, true));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
