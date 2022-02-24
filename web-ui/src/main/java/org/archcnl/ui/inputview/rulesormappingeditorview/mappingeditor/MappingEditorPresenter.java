package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor;

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
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.InvalidVariableNameException;
import org.archcnl.ui.common.ButtonClickResponder;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorPresenter;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorView;
import org.archcnl.ui.common.andtriplets.events.AddAndTripletsViewButtonPressedEvent;
import org.archcnl.ui.common.andtriplets.events.DeleteAndTripletsViewRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.dialogs.OkCancelDialog;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingCancelButtonClickedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingCloseButtonClicked;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDescriptionFieldChangedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDoneButtonClickedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingNameFieldChangedEvent;

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

    protected void initializeView(final MappingEditorView view) {
        this.view = view;
        addListeners();
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

    private void nameHasChanged(final MappingNameFieldChangedEvent event) {
        view.updateNameField(event.getNewName());
        view.updateNameFieldInThenTriplet(event.getNewName());
        updateMappingName(event.getNewName());
    }

    public void showNameAlreadyTakenErrorMessage() {
        view.showNameFieldErrorMessage("The name is already taken");
    }

    protected void showAndTriplets(final List<AndTriplets> andTripletsList) {
        view.clearContent();
        andTripletsPresenters.clear();
        andTripletsList.forEach(
                andTriplets -> {
                    AndTripletsEditorPresenter andTripletsPresenter =
                            new AndTripletsEditorPresenter(true);
                    AndTripletsEditorView andTripletsView =
                            prepareAndTripletsEditorView(andTripletsPresenter);
                    andTripletsPresenter.showAndTriplets(andTriplets);
                    view.addNewAndTripletsView(andTripletsView);
                });
    }

    protected AndTripletsEditorView prepareAndTripletsEditorView(
            final AndTripletsEditorPresenter andTripletsPresenter) {
        addListenersToAndTripletsPresenter(andTripletsPresenter);
        andTripletsPresenters.add(andTripletsPresenter);
        return andTripletsPresenter.getAndTripletsEditorView();
    }

    private void addListenersToAndTripletsPresenter(
            final AndTripletsEditorPresenter andTripletsPresenter) {
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

        andTripletsPresenter.addListener(PredicateSelectedEvent.class, this::fireEvent);
        andTripletsPresenter.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        andTripletsPresenter.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        andTripletsPresenter.addListener(ConceptSelectedEvent.class, this::fireEvent);
    }

    private void addNewAndTripletsViewAfter(final AndTripletsEditorView oldAndTripletsView) {
        view.addNewAndTripletsViewAfter(
                oldAndTripletsView,
                prepareAndTripletsEditorView(new AndTripletsEditorPresenter(true)));
    }

    private void deleteAndTripletsView(final AndTripletsEditorView andTripletsView) {
        final Optional<AndTripletsEditorPresenter> correspondingPresenter =
                findCorrespondingPresenter(andTripletsView);
        if (correspondingPresenter.isPresent()) {
            andTripletsPresenters.remove(correspondingPresenter.get());
        } else {
            MappingEditorPresenter.LOG.error(
                    "No corresponding TripletView found in AndTripletsEditorPresenter.");
        }
        view.deleteAndTripletsView(andTripletsView);
        if (andTripletsPresenters.isEmpty()) {
            lastAndTripletsViewDeleted();
        }
    }

    private Optional<AndTripletsEditorPresenter> findCorrespondingPresenter(
            final AndTripletsEditorView andTripletsView) {
        for (final AndTripletsEditorPresenter andTripletPresenter : andTripletsPresenters) {
            if (Objects.equals(andTripletPresenter.getAndTripletsEditorView(), andTripletsView)) {
                return Optional.of(andTripletPresenter);
            }
        }
        return Optional.empty();
    }

    private void lastAndTripletsViewDeleted() {
        view.addNewAndTripletsView(
                prepareAndTripletsEditorView(new AndTripletsEditorPresenter(true)));
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

    private void showIncompleteTripletsWarning(final ButtonClickResponder okResponse) {
        new OkCancelDialog(
                        "Warning: Incomplete rows will be lost",
                        "The \"When\" part of this Concept/Relation contains incomplete rows."
                                + "\nPressing \"OK\" will delete the incomplete rows."
                                + "\nPressing \"Cancel\" will highlight the incomplete rows.",
                        okResponse,
                        this::hightlightIncompleteTriplets)
                .open();
    }

    protected void addVariable(final VariableCreationRequestedEvent event) {
        try {
            final Variable newVariable = new Variable(event.getVariableName());
            variableManager.addVariable(newVariable);

            event.getSource()
                    .setItems(variableManager.getVariables().stream().map(Variable::getName));
            event.getSource().setValue(newVariable.getName());
            view.getVariableListView().showVariableList(variableManager.getVariables());
        } catch (final InvalidVariableNameException e1) {
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

    protected abstract void updateMappingName(String newName);

    protected abstract void updateInfoFieldsAndThenTriplet();

    protected abstract void updateMapping();

    protected abstract void descriptionHasChanged(MappingDescriptionFieldChangedEvent event);

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
