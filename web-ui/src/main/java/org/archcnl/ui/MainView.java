package org.archcnl.ui;

import org.archcnl.stardogwrapper.api.StardogAPIFactory;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogICVAPI;
import org.archcnl.stardogwrapper.impl.StardogDatabase;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * A sample Vaadin view class.
 *
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route annotation to announce
 * it in a URL as a Spring managed bean. Use the @PWA annotation make the application installable on
 * phones, tablets and some desktop browsers.
 *
 * <p>
 * A new instance of this class is created for every new user and every browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application", shortName = "Vaadin App",
    description = "This is an example Vaadin application.", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

  private static final long serialVersionUID = 1L;

  /**
   * Construct a new Vaadin view.
   *
   * <p>
   * Build the initial UI state for the user accessing the application.
   *
   * @param service The message service. Automatically injected Spring managed bean.
   */
  String query = "nothing";

  TextField selectField = new TextField();
  TextField subjectField = new TextField();
  TextField predicateField = new TextField();
  TextField objectField = new TextField();
  TextArea ta = new TextArea("test");
  StardogICVAPI icvAPI;
  StardogDatabaseAPI db;

  public MainView() {
    final String username = "admin";
    final String password = "admin";
    final String databaseName = "archcnl_it_db";
    final String server = "http://localhost:5820";

    this.db = new StardogDatabase(server, databaseName, username, password);
    this.icvAPI = StardogAPIFactory.getICVAPI(db);

    db.connect(false);
    // Use TextField for standard text input
    final TextField textField = new TextField("Your name");
    textField.addThemeName("bordered");

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
    // Button click listeners can be defined as lambda expressions

    // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
    addClassName("centered-content");

    final Button queryButton = new Button("Apply", e -> printQuery());

    add(textField);
    add(selectField, subjectField, predicateField, objectField);
    add(ta, queryButton);
  }

  void updateNumberOfTypes() {}

  void updateNumberOfPackages() {}

  void updateNumberOfRelationships() {}

  void updateNumberOfViolations() {}

  public void updateQuery() {
    query = selectField.getValue() + subjectField.getValue() + predicateField.getValue()
        + objectField.getValue();
  }

  public void printQuery() {
    System.out.println(query);
    final String q =
        " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> PREFIX conformance: <http://arch-ont.org/ontologies/architectureconformance#> PREFIX famix: <http://arch-ont.org/ontologies/famix.owl#> PREFIX architecture: <http://www.arch-ont.org/ontologies/architecture.owl#> SELECT DISTINCT ?cnl ?violation WHERE { GRAPH ?g { ?rule rdf:type conformance:ArchitectureRule.  ?rule conformance:hasRuleRepresentation ?cnl.  ?violation conformance:violates ?rule.  } }";
    try {
      this.db.executeSelectQuery(ta.getValue());
    } catch (final Exception e) {
      System.out.println("Exception Caught, fix this later");
      e.printStackTrace();
    }
  }
}
