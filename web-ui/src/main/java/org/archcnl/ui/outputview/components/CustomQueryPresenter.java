package org.archcnl.ui.outputview.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.domain.output.model.query.QueryUtils;
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
import org.archcnl.ui.outputview.events.PinQueryButtonPressedEvent;
import org.archcnl.ui.outputview.events.PinQueryRequestedEvent;
import org.archcnl.ui.outputview.events.RunButtonPressedEvent;
import org.archcnl.ui.outputview.events.RunQueryRequestedEvent;
import org.archcnl.ui.outputview.events.UpdateQueryTextButtonPressedEvent;

@Tag("CustomQueryPresenter")
public class CustomQueryPresenter extends Component {

    private static final long serialVersionUID = -2223963205535144587L;
    private static final String DEFAULT_NAME = "Pinned Custom Query";
    private CustomQueryView view;
    private AndTripletsEditorPresenter wherePresenter;
    private VariableManager variableManager;
    private String queryName;

    public CustomQueryPresenter() {
        queryName = DEFAULT_NAME;
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
        view.addListener(
                RunButtonPressedEvent.class,
                e ->
                        fireEvent(
                                new RunQueryRequestedEvent(
                                        this, true, makeQuery(), view.getGridView())));
        view.addListener(UpdateQueryTextButtonPressedEvent.class, this::handleEvent);
        view.addListener(
                PinQueryButtonPressedEvent.class,
                e -> {
                    queryName = view.getQueryName().orElse(DEFAULT_NAME);
                    view.setQueryName(queryName);
                    // TODO: Allow pinning after implementing cloning
                    view.setPinButtonVisible(false);
                    fireEvent(new PinQueryRequestedEvent(this, true));
                });

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

    private void handleEvent(UpdateQueryTextButtonPressedEvent event) {
        view.setQueryTextArea(getQuery());
    }

    private Query makeQuery() {
        Set<QueryNamespace> namespaces = QueryUtils.getNamespaces();
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

    private Optional<Variable> getOptionalVariable(
            VariableSelectionComponent variableSelectionComponent) {
        try {
            return Optional.of(variableSelectionComponent.getVariable());
        } catch (SubjectOrObjectNotDefinedException | InvalidVariableNameException e) {
            return Optional.empty();
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public String getQueryName() {
        return queryName;
    }
}
