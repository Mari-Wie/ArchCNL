package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.ConceptAndRelation;
import org.archcnl.ui.events.EditorRequestedEvent;

public class EditableHierarchyEntryLayout<T extends ConceptAndRelation>
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
        // TODO: Implement delete functionality
    }

    protected void editButtonPressed() {
        fireEvent(new EditorRequestedEvent(this, true));
    }
}
