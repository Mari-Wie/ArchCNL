package org.archcnl.ui.input.mappingeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;
import org.archcnl.ui.common.ButtonClickResponder;
import org.archcnl.ui.common.OkCancelDialog;
import org.archcnl.ui.input.events.RuleEditorRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.AddAndTripletsViewButtonPressedEvent;
import org.archcnl.ui.input.mappingeditor.events.DeleteAndTripletsViewRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.MappingCancelButtonClickedEvent;
import org.archcnl.ui.input.mappingeditor.events.MappingCloseButtonClicked;
import org.archcnl.ui.input.mappingeditor.events.MappingDescriptionFieldChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.MappingDoneButtonClickedEvent;
import org.archcnl.ui.input.mappingeditor.events.MappingNameFieldChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableCreationRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableFilterChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.exceptions.MappingAlreadyExistsException;

@Tag("Editor")
public abstract class MappingEditorPresenter extends Component {

    private static final long serialVersionUID = -9123529250149326943L;
    private static final Logger LOG = LogManager.getLogger(MappingEditorPresenter.class);
    private MappingEditorView view;
    private List<AndTripletsEditorPresenter> andTripletsPresenters = new LinkedList<>();
    protected VariableManager variableManager;

    protected MappingEditorPresenter() {
        this.variableManager = new VariableManager();
    }

    protected void initializeView(MappingEditorView view) {
        this.view = view;
        initInfoFieldAndThenTriplet();
        addListeners();
    }

    protected void initializeView(MappingEditorView view, List<AndTriplets> andTripletsList) {
        initializeView(view);
        showAndTriplets(andTripletsList);
    }

    private void addListeners() {
        view.addListener(
                MappingCloseButtonClicked.class,
                event -> fireEvent(new RuleEditorRequestedEvent(this, true)));
        view.addListener(MappingNameFieldChangedEvent.class, this::nameHasChanged);
        view.addListener(MappingDescriptionFieldChangedEvent.class, this::descriptionHasChanged);
        view.addListener(MappingDoneButtonClickedEvent.class, event -> doneButtonClicked());
        view.addListener(
                MappingCancelButtonClickedEvent.class,
                event -> fireEvent(new RuleEditorRequestedEvent(this, true)));
    }

    private void nameHasChanged(MappingNameFieldChangedEvent event) {
        view.updateNameField(event.getNewName());
        view.updateNameFieldInThenTriplet(event.getNewName());
        try {
            updateMappingName(event.getNewName());
        } catch (MappingAlreadyExistsException e) {
            view.showNameFieldErrorMessage("The name is already taken");
        }
    }

    private void showAndTriplets(List<AndTriplets> andTripletsList) {
        view.clearContent();
        andTripletsPresenters.clear();
        andTripletsList.forEach(
                andTriplets -> {
                    AndTripletsEditorPresenter andTripletsPresenter =
                            new AndTripletsEditorPresenter();
                    AndTripletsEditorView andTripletsView =
                            prepareAndTripletsEditorView(andTripletsPresenter);
                    andTripletsPresenter.showAndTriplets(andTriplets);
                    view.addNewAndTripletsView(andTripletsView);
                });
    }

    protected AndTripletsEditorView prepareAndTripletsEditorView(
            AndTripletsEditorPresenter andTripletsPresenter) {
        addListenersToAndTripletsPresenter(andTripletsPresenter);
        andTripletsPresenters.add(andTripletsPresenter);
        return andTripletsPresenter.getAndTripletsEditorView();
    }

    private void addListenersToAndTripletsPresenter(
            AndTripletsEditorPresenter andTripletsPresenter) {
        andTripletsPresenter.addListener(
                AddAndTripletsViewButtonPressedEvent.class,
                event -> addNewAndTripletsViewAfter(event.getSource()));
        andTripletsPresenter.addListener(
                DeleteAndTripletsViewRequestedEvent.class,
                event -> deleteAndTripletsView(event.getSource()));
        andTripletsPresenter.addListener(
                VariableFilterChangedEvent.class, event -> event.handleEvent(variableManager));
        andTripletsPresenter.addListener(VariableCreationRequestedEvent.class, this::addVariable);
        andTripletsPresenter.addListener(
                VariableListUpdateRequestedEvent.class,
                event -> event.handleEvent(variableManager));
    }

    private void addNewAndTripletsViewAfter(AndTripletsEditorView oldAndTripletsView) {
        view.addNewAndTripletsViewAfter(
                oldAndTripletsView, prepareAndTripletsEditorView(new AndTripletsEditorPresenter()));
    }

    private void deleteAndTripletsView(AndTripletsEditorView andTripletsView) {
        Optional<AndTripletsEditorPresenter> correspondingPresenter =
                findCorrespondingPresenter(andTripletsView);
        if (correspondingPresenter.isPresent()) {
            andTripletsPresenters.remove(correspondingPresenter.get());
        } else {
            LOG.error("No corresponding TripletView found in AndTripletsEditorPresenter.");
        }
        view.deleteAndTripletsView(andTripletsView);
        if (andTripletsPresenters.isEmpty()) {
            lastAndTripletsViewDeleted();
        }
    }

    private Optional<AndTripletsEditorPresenter> findCorrespondingPresenter(
            AndTripletsEditorView andTripletsView) {
        for (AndTripletsEditorPresenter andTripletPresenter : andTripletsPresenters) {
            if (Objects.equals(andTripletPresenter.getAndTripletsEditorView(), andTripletsView)) {
                return Optional.of(andTripletPresenter);
            }
        }
        return Optional.empty();
    }

    private void lastAndTripletsViewDeleted() {
        view.addNewAndTripletsView(prepareAndTripletsEditorView(new AndTripletsEditorPresenter()));
    }

    private void doneButtonClicked() {
        if (doIncompleteTripletsExist()) {
            showIncompleteTripletsWarning(() -> updateMapping());
        } else {
            updateMapping();
        }
    }

    private boolean doIncompleteTripletsExist() {
        return andTripletsPresenters.stream()
                .anyMatch(AndTripletsEditorPresenter::hasIncompleteTriplets);
    }

    private void showIncompleteTripletsWarning(ButtonClickResponder okResponse) {
        new OkCancelDialog(
                        "Warning: Incomplete rows will be lost",
                        "The \"When\" part of this Concept/Relation contains incomplete rows."
                                + "\nPressing \"OK\" will delete the incomplete rows."
                                + "\nPressing \"Cancel\" will highlight the incomplete rows.",
                        okResponse,
                        this::hightlightIncompleteTriplets)
                .open();
    }

    protected void addVariable(VariableCreationRequestedEvent event) {
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

    protected void hightlightIncompleteTriplets() {
        andTripletsPresenters.stream()
                .forEach(AndTripletsEditorPresenter::highlightIncompleteTriplets);
    }

    protected List<AndTriplets> getAndTripletsList() {
        return andTripletsPresenters.stream()
                .map(AndTripletsEditorPresenter::getAndTriplets)
                .collect(Collectors.toList());
    }

    public MappingEditorView getMappingEditorView() {
        return view;
    }

    protected abstract void updateMappingName(String newName) throws MappingAlreadyExistsException;

    protected abstract void initInfoFieldAndThenTriplet();

    protected abstract void updateMapping();

    protected abstract void descriptionHasChanged(MappingDescriptionFieldChangedEvent event);

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
