package org.vaadin.example.queryview;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;

public class QueryResults extends VerticalLayout {

    private WhereLayout whereLayout = new WhereLayout();
    private SelectLayout selectLayout = new SelectLayout();
    private Label selectLabel = new Label("Select");
    private Label whereLabel = new Label("Where");
    private HorizontalLayout selectLabelLayout = new HorizontalLayout(selectLabel);
    private HorizontalLayout whereLabelLayout =
            new HorizontalLayout(whereLabel, new HideButton(whereLayout));
    // private Button queryButton = new Button("Apply", e -> updateGrid());
    private Button queryButton = new Button("Apply");
    private Button clearButton = new Button("Clear", e -> whereLayout.clear());
    private GridView gridView = new GridView();
    private HideButton hideButton = new HideButton(gridView);
    private String exampleQuery =
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> PREFIX conformance: <http://arch-ont.org/ontologies/architectureconformance#> PREFIX famix: <http://arch-ont.org/ontologies/famix.owl#> PREFIX architecture: <http://www.arch-ont.org/ontologies/architecture.owl#> SELECT ?cnl ?violation ?name WHERE { GRAPH ?g { ?rule rdf:type conformance:ArchitectureRule.  ?rule conformance:hasRuleRepresentation ?cnl.  ?aggregate rdf:type architecture:Aggregate.  ?aggregate famix:hasName ?name.  ?violation conformance:violates ?rule.  } }";
    private TextArea queryTextArea = new TextArea("SPARQL Query");
    private HideButton hideQueryTextArea = new HideButton(queryTextArea);

    public <T extends ComponentEvent<?>> Registration addListener(
            Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public QueryResults() {

        queryButton.addClickListener(
                e -> {
                    fireEvent(new ResultUpdateEvent(this, false));
                });

        queryTextArea.setValue(exampleQuery);
        queryTextArea.setWidth(100, Unit.PERCENTAGE);
        whereLabel.setHeight(100, Unit.PERCENTAGE);
        add(
                selectLabelLayout,
                selectLayout,
                whereLabelLayout,
                whereLayout,
                new HorizontalLayout(queryButton, clearButton, hideButton),
                gridView,
                hideQueryTextArea,
                queryTextArea);
    }

    public void updateQueryString() {
        // TODO
    }

    void updateGrid(StardogDatabaseAPI.Result res) {
        gridView.update(res);
    }

    public String getQuery() {
        return queryTextArea.getValue();
    }
}
