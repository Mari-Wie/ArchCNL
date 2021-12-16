package org.archcnl.ui.input.mappingeditor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.ui.input.InputContract;
import org.archcnl.ui.input.mappingeditor.events.MappingDescriptionFieldChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableCreationRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableFilterChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.exceptions.MappingAlreadyExistsException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class ConceptEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = 1636256374259524105L;
    private ConceptEditorView view;
    private Optional<CustomConcept> concept;

    public ConceptEditorPresenter(InputContract.Remote inputRemote) {
        super(inputRemote);
        this.concept = Optional.empty();
        view =
                new ConceptEditorView(
                        prepareAndTripletsEditorView(new AndTripletsEditorPresenter()));
        initializeView(view);
        addThenTripletListeners();
    }

    public ConceptEditorPresenter(InputContract.Remote inputRemote, CustomConcept concept) {
        super(inputRemote);
        this.concept = Optional.of(concept);
        view =
                new ConceptEditorView(
                        prepareAndTripletsEditorView(new AndTripletsEditorPresenter()));
        initializeView(view, extractAndTriplets(concept));
        addThenTripletListeners();
    }

    private void addThenTripletListeners() {
        view.addListener(
                VariableFilterChangedEvent.class, event -> event.handleEvent(variableManager));
        view.addListener(VariableCreationRequestedEvent.class, this::addVariable);
        view.addListener(
                VariableListUpdateRequestedEvent.class,
                event -> event.handleEvent(variableManager));
    }

    private static List<AndTriplets> extractAndTriplets(CustomConcept concept) {
        if (concept == null || concept.getMapping().isEmpty()) {
            return new LinkedList<>();
        } else {
            return concept.getMapping().get().getWhenTriplets();
        }
    }

    @Override
    protected void initInfoFieldAndThenTriplet() {
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
    protected void updateMappingName(String newName) throws MappingAlreadyExistsException {
        if (concept.isEmpty()) {
            concept = Optional.of(new CustomConcept(newName, ""));
        } else {
            try {
                concept.get().changeName(newName);
            } catch (ConceptAlreadyExistsException e) {
                if (!newName.equals(concept.get().getName())) {
                    throw new MappingAlreadyExistsException(e.getMessage());
                }
            }
        }
    }

    @Override
    protected void updateMapping(InputContract.Remote inputRemote) {
        if (concept.isPresent()) {
            try {
                ConceptMapping mapping =
                        new ConceptMapping(
                                view.getThenTripletSubject(), getAndTripletsList(), concept.get());
                concept.get().setMapping(mapping);

                if (!doesConceptExist(concept.get())) {
                    RulesConceptsAndRelations.getInstance()
                            .getConceptManager()
                            .addConcept(concept.get());
                }

                inputRemote.switchToArchitectureRulesView();
            } catch (UnrelatedMappingException
                    | UnsupportedObjectTypeInTriplet
                    | RelationDoesNotExistException
                    | ConceptAlreadyExistsException
                    | InvalidVariableNameException e) {
                // not possible/fatal
                throw new RuntimeException();
            } catch (SubjectOrObjectNotDefinedException e) {
                view.showThenSubjectErrorMessage("Setting a subject is required");
            }
        } else {
            view.showNameFieldErrorMessage("A name is required");
        }
    }

    private boolean doesConceptExist(CustomConcept concept) {
        return RulesConceptsAndRelations.getInstance()
                .getConceptManager()
                .doesConceptExist(concept);
    }

    @Override
    public void descriptionHasChanged(MappingDescriptionFieldChangedEvent event) {
        concept.get().setDescription(event.getNewDescription());
    }
}
