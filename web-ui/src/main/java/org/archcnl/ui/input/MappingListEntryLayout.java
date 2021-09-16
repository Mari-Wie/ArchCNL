package org.archcnl.ui.input;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MappingListEntryLayout extends HorizontalLayout {

    private static final long serialVersionUID = 2L;

    private MappingListEntry entry;

    private final String EDIT_TEXT = "Edit";
    private final String DELETE_TEXT = "Delete";
    private Button editButton;
    private Button deleteButton;

    public MappingListEntryLayout(MappingListEntry entry) {
        this.entry = entry;

        Span text = new Span(entry.toString());
        text.setWidth("100%");
        addAndExpand(text);
        if (entry.isLeaf() && entry.isAlterable()) {
            editButton = new Button(EDIT_TEXT);
            deleteButton = new Button(DELETE_TEXT);
            add(editButton);
            add(deleteButton);
        }
    }

    public MappingListEntry getListEntry() {
        return entry;
    }
}
