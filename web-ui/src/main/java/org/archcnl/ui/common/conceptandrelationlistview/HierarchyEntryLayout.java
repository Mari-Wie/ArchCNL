package org.archcnl.ui.common.conceptandrelationlistview;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;

public class HierarchyEntryLayout<T extends HierarchyObject> extends HorizontalLayout {

    private static final long serialVersionUID = 2L;
    private HierarchyNode<T> entry;

    public HierarchyEntryLayout(final HierarchyNode<T> entry) {
        this.entry = entry;
        final Span text = new Span(entry.getName());
        text.setWidth(100, Unit.PERCENTAGE);
        addAndExpand(text);
        updateDescription();
    }

    public HierarchyNode<T> getListEntry() {
        return entry;
    }

    public T get() {
        return entry.getEntry();
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
