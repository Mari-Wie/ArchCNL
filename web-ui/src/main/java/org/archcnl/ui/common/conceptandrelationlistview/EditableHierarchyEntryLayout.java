package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteHierarchyObjectRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.EditorRequestedEvent;

public class EditableHierarchyEntryLayout<T extends HierarchyObject>
        extends HierarchyEntryLayout<T> {

    private Button editButton;
    private Button deleteButton;

    public EditableHierarchyEntryLayout(HierarchyNode<T> entry) {
        super(entry);
        editButton = new Button(new Icon(VaadinIcon.EDIT), click -> editButtonPressed());
        deleteButton = new Button(new Icon(VaadinIcon.TRASH), click -> deleteButtonPressed());
        add(editButton);
        add(deleteButton);
    }

    protected void deleteButtonPressed() {
        HierarchyObject hierarchyObject = get();
        fireEvent(new DeleteHierarchyObjectRequestedEvent(this, true, entry));
    }

    protected void editButtonPressed() {
        fireEvent(new EditorRequestedEvent(this, true));
    }
}
