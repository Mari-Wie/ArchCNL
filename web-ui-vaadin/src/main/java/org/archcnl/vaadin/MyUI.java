package org.archcnl.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.archcnl.stardogwrapper.api.StardogAPIFactory;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogICVAPI;
import org.archcnl.stardogwrapper.impl.StardogDatabase;

/**
 * This UI is the application entry point. A UI may either represent a browser window (or tab) or
 * some part of a html page where a Vaadin application is embedded.
 *
 * <p>The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField filterText = new TextField();
    private TextField selectField = new TextField();
    private TextField subjectField = new TextField();
    private TextField predicateField = new TextField();
    private TextField objectField = new TextField();
    private TextArea ta = new TextArea("test");
    private StardogICVAPI icvAPI;
    private StardogDatabaseAPI db;
    String query = "nothing";

    // private StardogDatabaseAPI db;
    // private StardogICVAPI icvAPI;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        String username = "admin";
        String password = "admin";
        String databaseName = "archcnl_it_db";
        String server = "http://localhost:5820";

        this.db = new StardogDatabase(server, databaseName, username, password);
        this.icvAPI = StardogAPIFactory.getICVAPI(db);

        db.connect(false);

        grid.setColumns("firstName", "lastName", "email");

        // add Grid to the layout
        layout.addComponents(grid);
        layout.addComponents(ta);

        Button queryButton = new Button(FontAwesome.TIMES);
        queryButton.setDescription("Apply");
        queryButton.addClickListener(e -> printQuery());

        layout.addComponent(queryButton);

        filterText.setPlaceholder("filter by name...");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        selectField.setPlaceholder("selectField");
        selectField.addValueChangeListener(e -> updateQuery());
        selectField.setValueChangeMode(ValueChangeMode.LAZY);

        subjectField.setPlaceholder("subjectField");
        subjectField.addValueChangeListener(e -> updateQuery());
        subjectField.setValueChangeMode(ValueChangeMode.LAZY);

        predicateField.setPlaceholder("predicate");
        predicateField.addValueChangeListener(e -> updateQuery());
        predicateField.setValueChangeMode(ValueChangeMode.LAZY);

        objectField.setPlaceholder("object");
        objectField.addValueChangeListener(e -> updateQuery());
        objectField.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        layout.addComponents(filtering, grid);
        layout.addComponents(selectField, subjectField, predicateField, objectField);
        // fetch list of Customers from service and assign it to Grid
        updateList();

        setContent(layout);
        // Create an opener extension
        BrowserWindowOpener opener = new BrowserWindowOpener(MyUI.class);
        opener.setFeatures("height=200,width=300,resizable");
    }

    void updateNumberOfTypes(){
    }
    void updateNumberOfPackages(){
    }
    void updateNumberOfRelationships(){}
    void updateNumberOfViolations(){}

    public void updateQuery() {
        query =
            selectField.getValue()
            + subjectField.getValue()
            + predicateField.getValue()
            + objectField.getValue();
    }

    public void printQuery() {
        System.out.println(query);
        String q =
            " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> PREFIX conformance: <http://arch-ont.org/ontologies/architectureconformance#> PREFIX famix: <http://arch-ont.org/ontologies/famix.owl#> PREFIX architecture: <http://www.arch-ont.org/ontologies/architecture.owl#> SELECT DISTINCT ?cnl ?violation WHERE { GRAPH ?g { ?rule rdf:type conformance:ArchitectureRule.  ?rule conformance:hasRuleRepresentation ?cnl.  ?violation conformance:violates ?rule.  } }";
        this.db.executeSelectQuery(ta.getValue());
    }

    public void updateList() {
        List<Customer> customers = service.findAll(filterText.getValue());
        grid.setItems(customers);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {}
}
