package org.archcnl.ui.common.conceptandrelationlistview.mappinglistlayout;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RelationEditorRequestedEvent;

public class MappingListEntryLayout extends HorizontalLayout {

    private static final long serialVersionUID = 2L;
    private MappingListEntry entry;
    private Button editButton;
    private Button deleteButton;

    public MappingListEntryLayout(final MappingListEntry entry) {
        this.entry = entry;
        setWidthFull(); // TODO: Tooltip only works when hovering over the text or buttons

        final Span text = new Span(entry.toString());
        text.setWidth("100%");
        addAndExpand(text);
        if (entry.isLeaf() && entry.isAlterable()) {
            editButton = new Button(new Icon(VaadinIcon.EDIT), click -> editButtonPressed());
            deleteButton = new Button(new Icon(VaadinIcon.TRASH), click -> deleteButtonPressed());
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
            final ConceptListEntry conceptListEntry = (ConceptListEntry) entry;
            fireEvent(
                    new ConceptEditorRequestedEvent(
                            this, true, (CustomConcept) conceptListEntry.getContent()));
        } else if (entry instanceof RelationListEntry) {
            final RelationListEntry relationListEntry = (RelationListEntry) entry;
            fireEvent(
                    new RelationEditorRequestedEvent(
                            this, true, (CustomRelation) relationListEntry.getContent()));
        }
    }

    public void updateDescription() {
        getElement().setAttribute("title", entry.getDescription());
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
