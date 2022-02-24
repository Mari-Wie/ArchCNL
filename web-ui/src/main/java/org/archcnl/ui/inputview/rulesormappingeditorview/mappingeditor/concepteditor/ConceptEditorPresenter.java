package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorPresenter;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.events.AddCustomConceptRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.events.ChangeConceptNameRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDescriptionFieldChangedEvent;

public class ConceptEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = 1636256374259524105L;
    private ConceptEditorView view;
    private Optional<CustomConcept> concept = Optional.empty();

    public ConceptEditorPresenter() {
        super();
        view =
                new ConceptEditorView(
                        prepareAndTripletsEditorView(new AndTripletsEditorPresenter(true)));
        initializeView(view);
        addConceptViewListeners();
    }

    public void showConcept(final CustomConcept concept) {
        this.concept = Optional.of(concept);
        showAndTriplets(extractAndTriplets(concept));
        updateInfoFieldsAndThenTriplet();
    }

    private void addConceptViewListeners() {
        view.addListener(
                VariableFilterChangedEvent.class, event -> event.handleEvent(variableManager));
        view.addListener(VariableCreationRequestedEvent.class, this::addVariable);
        view.addListener(
                VariableListUpdateRequestedEvent.class,
                event -> event.handleEvent(variableManager));
    }

    private List<AndTriplets> extractAndTriplets(final CustomConcept concept) {
        if (concept == null || concept.getMapping().isEmpty()) {
            return new LinkedList<>();
        } else {
            return concept.getMapping().get().getWhenTriplets();
        }
    }

    @Override
    protected void updateInfoFieldsAndThenTriplet() {
        if (concept.isPresent()) {
            view.updateDescription(concept.get().getDescription());
            view.updateNameField(concept.get().getName());
            view.updateNameFieldInThenTriplet(concept.get().getName());
            concept.get()
                    .getMapping()
                    .ifPresent(
                            mapping ->
                                    view.setSubjectInThenTriplet(
                                            mapping.getThenTriplet().getSubject()));
        }
    }

    @Override
    protected void updateMappingName(final String newName) {
        if (concept.isEmpty()) {
            concept = Optional.of(new CustomConcept(newName, ""));
        } else {
            fireEvent(new ChangeConceptNameRequestedEvent(this, true, concept.get(), newName));
        }
    }

    @Override
    protected void updateMapping() {
        if (concept.isPresent()) {
            try {
                final ConceptMapping mapping =
                        new ConceptMapping(
                                view.getThenTripletSubject(), getAndTripletsList(), concept.get());
                concept.get().setMapping(mapping);
                fireEvent(new AddCustomConceptRequestedEvent(this, true, concept.get()));

                fireEvent(new RuleEditorRequestedEvent(this, true));
            } catch (UnrelatedMappingException | InvalidVariableNameException e) {
                // not possible/fatal
                throw new RuntimeException(e.getMessage());
            } catch (final SubjectOrObjectNotDefinedException e) {
                view.showThenSubjectErrorMessage("Setting a subject is required");
            }
        } else {
            view.showNameFieldErrorMessage("A name is required");
        }
    }

    @Override
    public void descriptionHasChanged(final MappingDescriptionFieldChangedEvent event) {
        concept.get().setDescription(event.getNewDescription());
    }
}
