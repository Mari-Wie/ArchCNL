package org.archcnl.ui.outputview.components;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.output.model.query.QueryUtils;

public abstract class AbstractQueryResultsComponent extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger(AbstractQueryResultsComponent.class);

    protected GridView gridView;
    protected TextArea queryTextArea;

    protected AbstractQueryResultsComponent() throws PropertyNotFoundException {
        setHeightFull();
        getStyle().set("overflow", "auto");
        gridView = new GridView();
        queryTextArea = new TextArea("SPARQL Query");
        queryTextArea.setWidth(100, Unit.PERCENTAGE);
        String value = "";
        try {
            value = QueryUtils.getDefaultQuery().transformToGui();
        } catch (NoSuchElementException
                | UnsupportedObjectTypeInTriplet
                | InvalidVariableNameException e) {
            AbstractQueryResultsComponent.LOG.error(
                    "Construction of default query failed due to {}", e.getMessage());
        }
        queryTextArea.setValue(value);
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
