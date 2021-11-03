package org.archcnl.ui.input;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.domain.input.model.mappings.CustomConcept;
import org.archcnl.domain.input.model.mappings.CustomRelation;

public class MappingListEntryLayout extends HorizontalLayout {

    private static final long serialVersionUID = 2L;
    private MappingListEntry entry;
    private final String EDIT_TEXT = "Edit";
    private final String DELETE_TEXT = "Delete";
    private Button editButton;
    private Button deleteButton;
    private InputContract.Remote inputRemote;

    public MappingListEntryLayout(MappingListEntry entry, InputContract.Remote inputRemote) {
        this.entry = entry;
        this.inputRemote = inputRemote;
        setWidthFull(); // TODO: Tooltip only works when hovering over the text or buttons

        Span text = new Span(entry.toString());
        text.setWidth("100%");
        addAndExpand(text);
        if (entry.isLeaf() && entry.isAlterable()) {
            editButton = new Button(EDIT_TEXT, click -> editButtonPressed());
            deleteButton = new Button(DELETE_TEXT, click -> deleteButtonPressed());
            add(editButton);
            add(deleteButton);
            getElement().setAttribute("title", entry.getDescription());
        }
    }

    public MappingListEntry getListEntry() {
        return entry;
    }

    protected void deleteButtonPressed() {
        // TODO: Implement delete functionality
    }

    protected void editButtonPressed() {
        if (entry instanceof ConceptListEntry) {
            ConceptListEntry conceptListEntry = (ConceptListEntry) entry;
            inputRemote.switchToConceptEditorView((CustomConcept) conceptListEntry.getContent());
        } else if (entry instanceof RelationListEntry) {
            RelationListEntry relationListEntry = (RelationListEntry) entry;
            inputRemote.switchToRelationEditorView((CustomRelation) relationListEntry.getContent());
        }
    }

    public void updateDescription() {
        getElement().setAttribute("title", entry.getDescription());
    }
}
