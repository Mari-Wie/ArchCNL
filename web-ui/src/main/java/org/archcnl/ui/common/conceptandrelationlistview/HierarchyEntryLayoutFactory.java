package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;

public class HierarchyEntryLayoutFactory<T extends HierarchyObject> {

    public HierarchyEntryLayout<T> createStatic(HierarchyNode<T> node) {
        return new HierarchyEntryLayout<>(node);
    }

    public HierarchyEntryLayout<T> createRemovable(HierarchyNode<T> node) {
        HierarchyEntryLayout<T> newLayout = new HierarchyEntryLayout<>(node);
        if (node.getChildren().isEmpty()) {
            Button newButton =
                    new Button(new Icon(VaadinIcon.TRASH), click -> newLayout.handleDeleteEvent());
            // TODO button should not ne only invisible when children are there but should be greyed
            // out
            // newButton.setEnabled(false);
            // newButton.getElement().setProperty("title", "Nodes with more than 0 children cannot
            // be removed");
            newLayout.add(newButton);
        }
        return newLayout;
    }

    public HierarchyEntryLayout<T> createEditable(HierarchyNode<T> node) {
        HierarchyEntryLayout<T> newLayout = new HierarchyEntryLayout<>(node);
        newLayout.add(
                new Button(
                        new Icon(VaadinIcon.EDIT), click -> newLayout.handleEditorRequestEvent()));
        newLayout.add(
                new Button(new Icon(VaadinIcon.TRASH), click -> newLayout.handleDeleteEvent()));
        newLayout.add(
                new Button(
                        new Icon(VaadinIcon.PICTURE), click -> newLayout.handleVisualizeEvent()));
        return newLayout;
    }
}
