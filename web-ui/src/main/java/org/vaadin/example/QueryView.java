package org.vaadin.example;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import org.archcnl.stardogwrapper.api.StardogAPIFactory;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogICVAPI;
import org.archcnl.stardogwrapper.impl.StardogDatabase;

/**
 * A sample Vaadin view class.
 *
 * <p>To implement a Vaadin view just extend any Vaadin component and use @Route annotation to
 * announce it in a URL as a Spring managed bean. Use the @PWA annotation make the application
 * installable on phones, tablets and some desktop browsers.
 *
 * <p>A new instance of this class is created for every new user and every browser tab/window.
 */
@Route("QueryView")
public class QueryView extends HorizontalLayout {

    public class ResultUpdateEvent extends ComponentEvent<QueryResults> {
        public ResultUpdateEvent(QueryResults source, boolean fromClient) {
            super(source, fromClient);
            System.out.println("Result Fired");
        }
    }

    public class AddWhereLayoutRequestEvent extends ComponentEvent<WhereTextBoxesLayout> {
        public AddWhereLayoutRequestEvent(WhereTextBoxesLayout source, boolean fromClient) {
            super(source, fromClient);
            System.out.println("addWhereLayoutRequestEvent Fired");
        }
    }

    public class RemoveWhereLayoutRequestEvent extends ComponentEvent<WhereTextBoxesLayout> {
        public RemoveWhereLayoutRequestEvent(WhereTextBoxesLayout source, boolean fromClient) {
            super(source, fromClient);
            System.out.println("removeWhereLayoutRequestEvent Fired");
        }
    }

    private class SideBar extends VerticalLayout {
        SideBar() {
            getStyle().set("background-color", "#3458eb");
        }
    }

    private class SelectLayout extends VerticalLayout {
        private TextField selectTextField = new TextField();
        private String selectString = "";

        SelectLayout() {

            setAlignItems(Alignment.START);
            selectTextField.setPlaceholder("selectField");
            selectTextField.addValueChangeListener(e -> updateSelect());
            selectTextField.setValueChangeMode(ValueChangeMode.LAZY);
            add(selectTextField);
        }

        void updateSelect() {
            selectString = selectTextField.getValue();
        }

        String getValue() {
            return selectString;
        }
    }

    private class WhereTextBoxesLayout extends HorizontalLayout {
        private TextField subjectTextField = new TextField();
        private TextField objectTextField = new TextField();
        private TextField predicateTextField = new TextField();
        private Button addButton = new Button(new Icon(VaadinIcon.PLUS));
        private Button minusButton = new Button(new Icon(VaadinIcon.MINUS));
        private PauseButton pauseButton = new PauseButton(new Icon(VaadinIcon.PAUSE));
        private boolean isEnabled = true;
        private boolean isLast = false;

        WhereTextBoxesLayout() {
            addTextField(
                    "Subject",
                    subjectTextField,
                    e -> {
                        // TODO do something useful with this
                        System.out.println(subjectTextField.getValue());
                    });
            addTextField(
                    "Object",
                    objectTextField,
                    e -> {
                        // TODO do something useful with this
                        System.out.println(objectTextField.getValue());
                    });
            addTextField(
                    "Predicate",
                    predicateTextField,
                    e -> {
                        // TODO do something useful with this
                        System.out.println(predicateTextField.getValue());
                    });

            addButton.addClickListener(
                    e -> {
                        fireEvent(new AddWhereLayoutRequestEvent(this, false));
                    });
            minusButton.addClickListener(
                    e -> {
                        fireEvent(new RemoveWhereLayoutRequestEvent(this, false));
                    });
            pauseButton.addClickListener(
                    e -> {
                        isEnabled = !isEnabled;
                    });
            add(addButton);
            add(minusButton);
            add(pauseButton);
        }

        void addTextField(
                String placeHolder,
                TextField textField,
                HasValue.ValueChangeListener<
                                ? super AbstractField.ComponentValueChangeEvent<TextField, String>>
                        listener) {
            textField.setPlaceholder(placeHolder);
            textField.addValueChangeListener(listener);
            textField.setValueChangeMode(ValueChangeMode.LAZY);
            add(textField);
        }

        public List<String> getObjSubPraedString() {
            return Arrays.asList(
                    subjectTextField.getValue(),
                    objectTextField.getValue(),
                    predicateTextField.getValue());
        }

        public <T extends ComponentEvent<?>> Registration addListener(
                Class<T> eventType, ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
        }
    }

    private class WhereLayout extends VerticalLayout {
        public WhereLayout() {
            addWhereTextLayout(0);
        }

        public void removeRow(WhereTextBoxesLayout layout) {
            remove(layout);
        }

        public void clear() {
            removeAll();
            addWhereTextLayout(0);
        }
        ;

        public void addWhereTextLayout(int position) {
            WhereTextBoxesLayout newLayout = new WhereTextBoxesLayout();
            int newPosition = position;
            if (newPosition < getComponentCount()) {
                newPosition += 1;
            }

            addComponentAtIndex(newPosition, newLayout);

            Registration regAdd =
                    newLayout.addListener(
                            AddWhereLayoutRequestEvent.class,
                            e -> {
                                addWhereTextLayout(indexOf(e.getSource()));
                            });
            Registration regMinus =
                    newLayout.addListener(
                            RemoveWhereLayoutRequestEvent.class,
                            e -> {
                                removeRow(e.getSource());
                            });
        }
    }

    private class QueryResults extends VerticalLayout {

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
        private TextArea queryTextArea = new TextArea("SQRL Query");
        private HideButton hideQueryTextArea = new HideButton(queryTextArea);

        public <T extends ComponentEvent<?>> Registration addListener(
                Class<T> eventType, ComponentEventListener<T> listener) {
            return getEventBus().addListener(eventType, listener);
        }

        QueryResults() {

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

        public void updateViolationList() {
            // TODO

        }

        public String getQuery() {
            return queryTextArea.getValue();
        }
    }

    public class Violation {
        TreeMap<String, String> violation = new TreeMap<String,String>();

        Violation(List<String> vars, ArrayList<String> values) {
            for (int i = 0; i < vars.size(); i++) {
                violation.put(vars.get(i), values.get(i));
            }
        }

        public String getVal(String key) {
            return violation.get(key);
        }
    }

    private class GridView extends VerticalLayout {

        private ArrayList<Violation> violationList = new ArrayList<>();
        private Grid<Violation> grid = new Grid<>(Violation.class);

        GridView() {
            grid.setHeightByRows(true);
            add(grid);
        }

        public void update(StardogDatabaseAPI.Result results) {
            try {
                if (results.getNumberOfViolations() > 0) {
                    for (String varName : results.getVars()) {
                        grid.addColumn(item -> item.getVal(varName)).setHeader(varName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<ArrayList<String>> violations = results.getViolations();
            for (ArrayList<String> violation : violations) {
                violationList.add(new Violation(results.getVars(), violation));
            }

            grid.setItems(violationList);
        }
    }
    // ALL THE MEMBERS OF QUERY VIEW

    SideBar sideBar = new SideBar();
    QueryResults queryResults = new QueryResults();

    // TODO Extract into interface
    //
    String username = "admin";
    String password = "admin";
    String databaseName = "archcnl_it_db";
    String server = "http://localhost:5820";
    StardogICVAPI icvAPI;
    StardogDatabaseAPI db;

    public QueryView() {
        this.db = new StardogDatabase(server, databaseName, username, password);
        this.icvAPI = StardogAPIFactory.getICVAPI(db);

        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        sideBar.setWidth(20, Unit.PERCENTAGE);
        queryResults.setWidth(80, Unit.PERCENTAGE);
        addAndExpand(sideBar, queryResults);
        Registration reg =
                queryResults.addListener(
                        ResultUpdateEvent.class,
                        e -> {
                            Optional<StardogDatabaseAPI.Result> res = Optional.empty();
                            System.out.println("Event Received");
                            String q = queryResults.getQuery();
                            try {
                                db.connect(false);
                                res = db.executeSelectQuery(q);
                                db.closeConnectionToServer();
                            } catch (Exception lol) {
                                System.out.println("Exception Caught, fix this later");
                                lol.printStackTrace();
                            }
                            if (res.isPresent()) {
                                queryResults.updateGrid(res.get());
                            } else {
                                // TODO Decide what to do with errors or wrong queries
                            }
                        });
    }
}
