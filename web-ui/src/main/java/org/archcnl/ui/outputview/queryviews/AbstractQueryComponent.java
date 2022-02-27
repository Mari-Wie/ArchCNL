package org.archcnl.ui.outputview.queryviews;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.outputview.queryviews.components.GridView;

public abstract class AbstractQueryComponent extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    protected GridView gridView;
    protected TextArea queryTextArea;

    protected AbstractQueryComponent(String defaultQueryText) {
        setHeightFull();
        getStyle().set("overflow", "auto");
        gridView = new GridView();
        queryTextArea = new TextArea("SPARQL Query");
        queryTextArea.setWidth(100, Unit.PERCENTAGE);
        queryTextArea.setValue("QUERY UNINITIALIZED");
        queryTextArea.setValue(defaultQueryText);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public String getQuery() {
        return queryTextArea.getValue();
    }

    public void setQueryText(final String text) {
        queryTextArea.setValue(text);
    }

    protected abstract void addComponents();
}
