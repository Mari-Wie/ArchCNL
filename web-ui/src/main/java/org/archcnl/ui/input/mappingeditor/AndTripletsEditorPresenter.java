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
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.ui.input.mappingeditor.events.AddAndTripletsViewButtonPressedEvent;
import org.archcnl.ui.input.mappingeditor.events.AddTripletViewAfterButtonPressedEvent;
import org.archcnl.ui.input.mappingeditor.events.DeleteAndTripletsViewRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.TripletViewDeleteButtonPressedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableCreationRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableFilterChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.exceptions.TripletNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.TripletPresenter;
import org.archcnl.ui.input.mappingeditor.triplet.TripletView;

@Tag("AndTripletsEditorPresenter")
public class AndTripletsEditorPresenter extends Component {

    private static final long serialVersionUID = 3037437189766673164L;
    private static final Logger LOG = LogManager.getLogger(AndTripletsEditorPresenter.class);
    private AndTripletsEditorView view;
    private List<TripletPresenter> tripletPresenters = new LinkedList<>();

    public AndTripletsEditorPresenter() {
        view = new AndTripletsEditorView(prepareTripletView(new TripletPresenter()));
        addListeners();
    }

    public void showAndTriplets(AndTriplets andTriplets) {
        showTriplets(andTriplets);
    }

    private void addListeners() {
        view.addListener(AddAndTripletsViewButtonPressedEvent.class, this::fireEvent);
        view.addListener(DeleteAndTripletsViewRequestedEvent.class, this::fireEvent);
    }

    public AndTriplets getAndTriplets() {
        List<Triplet> triplets =
                tripletPresenters.stream()
                        .map(
                                presenter -> {
                                    try {
                                        return presenter.getTriplet();
                                    } catch (TripletNotDefinedException
                                            | UnsupportedObjectTypeInTriplet e) {
                                        // ignore this incomplete/faulty Triplet
                                        return null;
                                    }
                                })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        return new AndTriplets(triplets);
    }

    private void showTriplets(AndTriplets andTriplets) {
        if (!andTriplets.getTriplets().isEmpty()) {
            view.clearContent();
            andTriplets
                    .getTriplets()
                    .forEach(
                            triplet -> {
                                TripletPresenter tripletPresenter = new TripletPresenter();
                                view.addTripletView(prepareTripletView(tripletPresenter));
                                tripletPresenter.showTriplet(triplet);
                            });
        }
    }

    public boolean hasIncompleteTriplets() {
        return tripletPresenters.stream().anyMatch(TripletPresenter::isIncomplete);
    }

    public void highlightIncompleteTriplets() {
        tripletPresenters.forEach(TripletPresenter::highlightIncompleteParts);
    }

    public AndTripletsEditorView getAndTripletsEditorView() {
        return view;
    }

    private TripletView prepareTripletView(TripletPresenter tripletPresenter) {
        addListenersToTripletPresenter(tripletPresenter);
        tripletPresenters.add(tripletPresenter);
        return tripletPresenter.getTripletView();
    }

    private void addListenersToTripletPresenter(TripletPresenter tripletPresenter) {
        tripletPresenter.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        tripletPresenter.addListener(VariableCreationRequestedEvent.class, this::fireEvent);
        tripletPresenter.addListener(VariableListUpdateRequestedEvent.class, this::fireEvent);
        tripletPresenter.addListener(
                TripletViewDeleteButtonPressedEvent.class,
                event -> deleteTripletView(event.getSource()));
        tripletPresenter.addListener(
                AddTripletViewAfterButtonPressedEvent.class,
                event -> addNewTripletViewAfter(event.getSource()));
    }

    private void addNewTripletViewAfter(TripletView oldTripletView) {
        view.addNewTripletViewAfter(oldTripletView, prepareTripletView(new TripletPresenter()));
    }

    private void deleteTripletView(TripletView tripletView) {
        Optional<TripletPresenter> correspondingPresenter = findCorrespondingPresenter(tripletView);
        if (correspondingPresenter.isPresent()) {
            tripletPresenters.remove(correspondingPresenter.get());
        } else {
            LOG.error("No corresponding TripletView found in AndTripletsEditorPresenter.");
        }
        view.deleteTripletView(tripletView);
        if (tripletPresenters.isEmpty()) {
            fireEvent(new DeleteAndTripletsViewRequestedEvent(view, false));
        }
    }

    private Optional<TripletPresenter> findCorrespondingPresenter(TripletView tripletView) {
        for (TripletPresenter tripletPresenter : tripletPresenters) {
            if (Objects.equals(tripletPresenter.getTripletView(), tripletView)) {
                return Optional.of(tripletPresenter);
            }
        }
        return Optional.empty();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
