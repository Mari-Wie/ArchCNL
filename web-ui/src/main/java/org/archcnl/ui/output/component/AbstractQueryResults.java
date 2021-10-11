package org.archcnl.ui.output.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.output.events.ResultUpdateEvent;

abstract class AbstractQueryResults extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    protected Registration reg;
    protected GridView gridView;
    protected String exampleQuery =
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                    + "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
                    + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                    + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                    + "PREFIX conformance: <http://arch-ont.org/ontologies/architectureconformance#>\n"
                    + "PREFIX famix: <http://arch-ont.org/ontologies/famix.owl#>\n"
                    + "PREFIX architecture: <http://www.arch-ont.org/ontologies/architecture.owl#>\n"
                    + "\n"
                    + "SELECT ?cnl ?violation ?name\n"
                    + "WHERE { \n"
                    + "GRAPH ?g { \n"
                    + "\t?rule rdf:type conformance:ArchitectureRule. \n"
                    + "\t?rule conformance:hasRuleRepresentation ?cnl. \n"
                    + "\t?aggregate rdf:type architecture:Aggregate. \n"
                    + "\t?aggregate famix:hasName ?name. \n"
                    + "\t?violation conformance:violates ?rule. \n"
                    + "} }";
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
