package org.archcnl.ui.output.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.domain.output.model.query.SelectClause;
import org.archcnl.domain.output.model.query.WhereClause;
import org.archcnl.domain.output.model.query.attribute.QueryNamespace;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorPresenter;
import org.archcnl.ui.common.andtriplets.triplet.VariableSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.output.events.ResultUpdateEvent;

@Tag("CustomQueryPresenter")
public class CustomQueryPresenter extends Component {

    private static final long serialVersionUID = -2223963205535144587L;
    private CustomQueryView view;
    private AndTripletsEditorPresenter wherePresenter;
    private VariableManager variableManager;

    public CustomQueryPresenter() throws PropertyNotFoundException {
        wherePresenter = new AndTripletsEditorPresenter(false);
        view = new CustomQueryView(wherePresenter.getAndTripletsEditorView());
        variableManager = new VariableManager();

        addListeners();
    }

    private void addListeners() {
        view.addListener(
                VariableFilterChangedEvent.class, event -> event.handleEvent(variableManager));
        view.addListener(VariableCreationRequestedEvent.class, this::addVariable);
        view.addListener(
                VariableListUpdateRequestedEvent.class,
                event -> event.handleEvent(variableManager));
        view.addListener(VariableSelectedEvent.class, this::handleEvent);
        view.addListener(ResultUpdateEvent.class, this::handleEvent);

        wherePresenter.addListener(
                VariableFilterChangedEvent.class, event -> event.handleEvent(variableManager));
        wherePresenter.addListener(VariableCreationRequestedEvent.class, this::addVariable);
        wherePresenter.addListener(
                VariableListUpdateRequestedEvent.class,
                event -> event.handleEvent(variableManager));
    }

    private void addVariable(VariableCreationRequestedEvent event) {
        try {
            Variable newVariable = new Variable(event.getVariableName());
            try {
                variableManager.addVariable(newVariable);
            } catch (VariableAlreadyExistsException e) {
                // do nothing
            }
            event.getSource()
                    .setItems(variableManager.getVariables().stream().map(Variable::getName));
            event.getSource().setValue(newVariable.getName());
            view.getVariableListView().showVariableList(variableManager.getVariables());
        } catch (InvalidVariableNameException e1) {
            event.getSource().showErrorMessage("Invalid variable name");
        }
    }

    private void handleEvent(VariableSelectedEvent event) {
        if (!view.isAnyVariableSelectionComponentEmpty()) {
            view.addVariableSelectionComponent();
        } else if (event.getSource().getOptionalValue().isEmpty()
                && view.areAtleastTwoVariableSelectionComponentsEmpty()) {
            view.removeVariableSelectionComponent(event.getSource());
        }
    }

    private void handleEvent(ResultUpdateEvent event) {
        view.getGridView().update(getQuery());
    }

    private Query makeQuery() {
        Set<QueryNamespace> namespaces = getNamespaces();
        SelectClause selectClause =
                new SelectClause(
                        view.getSelectComponents().stream()
                                .map(this::getOptionalVariable)
                                .flatMap(Optional::stream)
                                .collect(Collectors.toSet()));
        WhereClause whereClause = new WhereClause(wherePresenter.getAndTriplets());
        return new Query(namespaces, selectClause, whereClause);
    }

    public CustomQueryView getView() {
        return view;
    }

    public String getQuery() {
        return makeQuery().transformToGui();
    }

    private Set<QueryNamespace> getNamespaces() {
        final QueryNamespace rdf =
                new QueryNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns");
        final QueryNamespace owl = new QueryNamespace("owl", "http://www.w3.org/2002/07/owl");
        final QueryNamespace rdfs =
                new QueryNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema");
        final QueryNamespace xsd = new QueryNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
        final QueryNamespace conformance =
                new QueryNamespace(
                        "conformance", "http://arch-ont.org/ontologies/architectureconformance");
        final QueryNamespace famix =
                new QueryNamespace("famix", "http://arch-ont.org/ontologies/famix.owl");
        final QueryNamespace architecture =
                new QueryNamespace(
                        "architecture", "http://www.arch-ont.org/ontologies/architecture.owl");
        final QueryNamespace main =
                new QueryNamespace("main", "http://arch-ont.org/ontologies/main.owl");
        return new LinkedHashSet<>(
                Arrays.asList(rdf, owl, rdfs, xsd, conformance, famix, architecture, main));
    }

    private Optional<Variable> getOptionalVariable(
            VariableSelectionComponent variableSelectionComponent) {
        try {
            return Optional.of(variableSelectionComponent.getVariable());
        } catch (SubjectOrObjectNotDefinedException | InvalidVariableNameException e) {
            return Optional.empty();
        }
    }
}
