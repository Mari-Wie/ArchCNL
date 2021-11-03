package org.archcnl.ui.input.mappingeditor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.input.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.AndTriplets;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.CustomConcept;
import org.archcnl.ui.input.InputContract.Remote;
import org.archcnl.ui.input.mappingeditor.exceptions.MappingAlreadyExistsException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class ConceptEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = 1636256374259524105L;
    private Optional<CustomConcept> concept;

    public ConceptEditorPresenter() {
        super(extractAndTriplets(null));
        this.concept = Optional.ofNullable(null);
    }

    public ConceptEditorPresenter(CustomConcept concept) {
        super(extractAndTriplets(concept));
        this.concept = Optional.of(concept);
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
            concept = Optional.of(new CustomConcept(newName));
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
    public void selectedObjectTypeHasChanged() {
        throw new UnsupportedOperationException(
                "The selected ObjectType cannot change for a Concept.");
    }

    @Override
    protected void createOrUpdateMapping(Remote inputRemote) {
        if (concept.isPresent()) {
            try {
                ConceptMapping mapping =
                        new ConceptMapping(
                                view.getThenTripletSubject(), getAndTriplets(), concept.get());
                concept.get().setMapping(mapping);
                concept.get().setDescription(view.getDescription());
                if (!RulesConceptsAndRelations.getInstance()
                        .getConceptManager()
                        .doesConceptExist(concept.get())) {
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
}
