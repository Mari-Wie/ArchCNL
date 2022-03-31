package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteHierarchyObjectRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.EditorRequestedEvent;

public class HierarchyEntryLayout<T extends HierarchyObject> extends HorizontalLayout {

    private static final long serialVersionUID = 2L;
    protected HierarchyNode<T> entry;

    public HierarchyEntryLayout(final HierarchyNode<T> entry) {
        this.entry = entry;
        final Span text = new Span(entry.getName());
        text.setWidth(100, Unit.PERCENTAGE);
        addAndExpand(text);
        updateDescription();
    }

    public T get() {
        return entry.getEntry();
    }

    @Override
    public String toString() {
        return "HierarchyNode for" + entry.toString();
    }

    public void updateDescription() {
        getElement().setAttribute("title", entry.getDescription());
    }

    public void handleDelteEvent() {
        fireEvent(new DeleteHierarchyObjectRequestedEvent(this, true, entry));
    }

    public void handleEditorRequestEvent() {
        fireEvent(new EditorRequestedEvent(this, true));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
