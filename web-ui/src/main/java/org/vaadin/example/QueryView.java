package org.vaadin.example;

import com.vaadin.flow.component.AbstractField;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private class SideBar extends VerticalLayout {
        SideBar() {
            getStyle().set("background-color", "#3458eb");
        }
    }

    private class SelectLayout extends HorizontalLayout {
        private TextField selectTextField = new TextField();
        private String selectString = "";

        SelectLayout() {

            selectTextField.setPlaceholder("selectField");
            selectTextField.setLabel("Select");
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

        WhereTextBoxesLayout() {
            addTextField(
                    "Subject",
                    subjectTextField,
                    e -> {
                        System.out.println(subjectTextField.getValue());
                    });
            addTextField(
                    "Object",
                    objectTextField,
                    e -> {
                        System.out.println(objectTextField.getValue());
                    });
            addTextField(
                    "Predicate",
                    predicateTextField,
                    e -> {
                        System.out.println(predicateTextField.getValue());
                    });
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
    }

    private class WhereLayout extends VerticalLayout {
        ArrayList<WhereTextBoxesLayout> whereTextLayoutList = new ArrayList<WhereTextBoxesLayout>();
        // private Button disableButton = new Button(new Icon(VaadinIcon.PAUSE));
        public WhereLayout() {
            addWhereTextLayout();
        }

        public void remove(WhereTextBoxesLayout layout) {
            whereTextLayoutList.remove(layout);
        }

        public void clear() {
            removeAll();
            whereTextLayoutList.clear();
            addWhereTextLayout();
        }

        public void addWhereTextLayout() {
            WhereTextBoxesLayout newLayout = new WhereTextBoxesLayout();
            Button addButton = new Button(new Icon(VaadinIcon.PLUS), e -> addWhereTextLayout());
            newLayout.add(addButton);
            whereTextLayoutList.add(newLayout);
            add(newLayout);
        }
    }

    private class QueryResults extends VerticalLayout {

        private SelectLayout selectLayout = new SelectLayout();
        private Label whereLabel = new Label("Where");
        private WhereLayout whereLayout = new WhereLayout();
        private Button queryButton = new Button("Apply", e -> updateGrid());
        private Button clearButton = new Button("Clear", e -> clearQuery());
        private GridView gridView = new GridView();
        private TextArea queryTextArea = new TextArea("test");

        String query;

        //TODO Extract into interface
        StardogICVAPI icvAPI;
        StardogDatabaseAPI db;

        QueryResults() {
            //TODO move these stuff somewhere usefull also dont have it hardcoded somewhere
            String username = "admin";
            String password = "admin";
            String databaseName = "archcnl_it_db";
            String server = "http://localhost:5820";

            this.db = new StardogDatabase(server, databaseName, username, password);
            this.icvAPI = StardogAPIFactory.getICVAPI(db);

            queryTextArea.setWidth(100, Unit.PERCENTAGE);
            add(
                    selectLayout,
                    whereLabel,
                    whereLayout,
                    new HorizontalLayout(queryButton, clearButton),
                    gridView,
                    queryTextArea);
        }

        public void updateQueryString() {
            // TODO
        }

        public void clearQuery() {
            whereLayout.clear();
        }

        void updateGrid() {
            // gridView.update();
            printQuery();
        }

        public void updateViolationList() {
            // TODO

        }

        public void printQuery() {
            System.out.println(query);
            // String q =
            //    " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl:
            // <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
            // PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> PREFIX conformance:
            // <http://arch-ont.org/ontologies/architectureconformance#> PREFIX famix:
            // <http://arch-ont.org/ontologies/famix.owl#> PREFIX architecture:
            // <http://www.arch-ont.org/ontologies/architecture.owl#> SELECT DISTINCT ?cnl
            // ?violation WHERE { GRAPH ?g { ?rule rdf:type conformance:ArchitectureRule.  ?rule
            // conformance:hasRuleRepresentation ?cnl.  ?violation conformance:violates ?rule.  }
            // }";
            String q = queryTextArea.getValue();
            try {
                db.connect(false);
                this.db.executeSelectQuery(q);
                db.closeConnectionToServer();
            } catch (Exception e) {
                System.out.println("Exception Caught, fix this later");
                e.printStackTrace();
            }
        }
    }

    public class Violation {
        private final String line;
        private final String type;
        private final String name;
        private final String desc;

        Violation(String line, String type, String name, String desc) {
            this.line = line;
            this.type = type;
            this.name = name;
            this.desc = desc;
        }

        public String getLine() {
            return line;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }
    }

    private class GridView extends VerticalLayout {

        private ArrayList<Violation> violationList = new ArrayList<>();
        private Grid<Violation> grid = new Grid<>(Violation.class);

        GridView() {
            update();
            try {
                grid.addColumn(Violation::getLine).setHeader("Line");
                grid.addColumn(Violation::getType).setHeader("Type");
                grid.addColumn(Violation::getName).setHeader("Name");
                grid.addColumn(Violation::getDesc).setHeader("Desc");
            } catch (Exception e) {
                e.printStackTrace();
            }
            add(grid);
        }

        public void update() {

            violationList.add(new Violation("100", "Lucas", "Kane", "68"));
            violationList.add(new Violation("101", "Peter", "Buchanan", "38"));
            violationList.add(new Violation("102", "Samuel", "Lee", "53"));
            violationList.add(new Violation("103", "Anton", "Ross", "37"));
            violationList.add(new Violation("104", "Aaron", "Atkinson", "18"));
            violationList.add(new Violation("105", "Jack", "Woodward", "28"));
            grid.setItems(violationList);
        }
    }

    SideBar sideBar = new SideBar();
    QueryResults queryResults = new QueryResults();

    public QueryView() {
        setWidth(100, Unit.PERCENTAGE);
        sideBar.setWidth(20, Unit.PERCENTAGE);
        queryResults.setWidth(80, Unit.PERCENTAGE);
        add(sideBar, queryResults);
    }
}
