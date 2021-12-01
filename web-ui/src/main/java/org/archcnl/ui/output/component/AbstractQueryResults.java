package org.archcnl.ui.output.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.output.events.ResultUpdateEvent;

public abstract class AbstractQueryResults extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    protected Registration reg;
    protected GridView gridView;
    protected String exampleQuery =
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> PREFIX conformance: <http://arch-ont.org/ontologies/architectureconformance#> PREFIX famix: <http://arch-ont.org/ontologies/famix.owl#> PREFIX architecture: <http://www.arch-ont.org/ontologies/architecture.owl#> SELECT ?cnl ?violation ?name WHERE { GRAPH ?g { ?rule rdf:type conformance:ArchitectureRule.  ?rule conformance:hasRuleRepresentation ?cnl.  ?aggregate rdf:type architecture:Aggregate.  ?aggregate famix:hasName ?name.  ?violation conformance:violates ?rule.  } }";
    protected TextArea queryTextArea;

    public AbstractQueryResults() {
        setHeightFull();
        getStyle().set("overflow", "auto");
        gridView = new GridView();
        queryTextArea = new TextArea("SPARQL Query");
        queryTextArea.setValue(exampleQuery);
        queryTextArea.setWidth(100, Unit.PERCENTAGE);
     
        registerEventListeners();
    }

    void updateGrid() {
        gridView.update(queryTextArea.getValue());
    }

    protected void registerEventListeners() {
        addListener(ResultUpdateEvent.class, e -> this.updateGrid());
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public String getQuery() {
        return queryTextArea.getValue();
    }

    protected abstract void addComponents();
}
