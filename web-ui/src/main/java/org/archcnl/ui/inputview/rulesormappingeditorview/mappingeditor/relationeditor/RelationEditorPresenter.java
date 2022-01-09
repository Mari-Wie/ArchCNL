package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.ActualObjectType;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.ui.inputview.InputContract;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.AndTripletsEditorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.events.VariableCreationRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.events.VariableFilterChangedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.triplets.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDescriptionFieldChangedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.exceptions.MappingAlreadyExistsException;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public class RelationEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = -8403313455385623145L;
    private RelationEditorView view;
    private Optional<CustomRelation> relation;

    public RelationEditorPresenter(InputContract.Remote inputRemote) {
        super(inputRemote);
        this.relation = Optional.empty();
        view =
                new RelationEditorView(
                        prepareAndTripletsEditorView(new AndTripletsEditorPresenter()));
        addThenTripletListeners();
        initializeView(view);
    }

    public RelationEditorPresenter(InputContract.Remote inputRemote, CustomRelation relation) {
        super(inputRemote);
        this.relation = Optional.of(relation);
        view =
                new RelationEditorView(
                        prepareAndTripletsEditorView(new AndTripletsEditorPresenter()));
        addThenTripletListeners();
        initializeView(view, extractAndTriplets(relation));
    }

    private void addThenTripletListeners() {
        view.addListener(
                VariableFilterChangedEvent.class, event -> event.handleEvent(variableManager));
        view.addListener(VariableCreationRequestedEvent.class, this::addVariable);
        view.addListener(
                VariableListUpdateRequestedEvent.class,
                event -> event.handleEvent(variableManager));
    }

    private static List<AndTriplets> extractAndTriplets(CustomRelation relation) {
        if (relation == null || relation.getMapping().isEmpty()) {
            return new LinkedList<>();
        } else {
            return relation.getMapping().get().getWhenTriplets();
        }
    }

    // Stub
    @Override
    protected void updateMappingName(String newName) throws MappingAlreadyExistsException {
        if (relation.isEmpty()) {
            List<ActualObjectType> relatableObjectTypes = new LinkedList<>();
            ObjectType selectedObjectType = view.getSelectedObjectTypeInThenTriplet();
            if (selectedObjectType instanceof ActualObjectType) {
                relatableObjectTypes.add((ActualObjectType) selectedObjectType);
            }
            relation = Optional.of(new CustomRelation(newName, "", relatableObjectTypes));
        } else {
            try {
                relation.get().changeName(newName);
            } catch (RelationAlreadyExistsException e) {
                if (!newName.equals(relation.get().getName())) {
                    throw new MappingAlreadyExistsException(e.getMessage());
                }
            }
        }
    }

    @Override
    protected void initInfoFieldAndThenTriplet() {
        if (relation.isPresent()) {
            view.updateNameField(relation.get().getName());
            view.updateDescription(relation.get().getDescription());
            view.updateNameFieldInThenTriplet(relation.get().getName());
            relation.get()
                    .getMapping()
                    .ifPresent(
                            mapping -> {
                                view.setSubjectInThenTriplet(mapping.getThenTriplet().getSubject());
                                view.setObjectInThenTriplet(mapping.getThenTriplet().getObject());
                            });
        }
    }

    @Override
    protected void updateMapping(InputContract.Remote inputRemote) {
        if (relation.isPresent()) {
            try {
                CustomRelation thenRelation = relation.get();
                ObjectType thenObject = view.getThenTripletObject();
                thenRelation.setRelatableObjectType(thenObject);
                Triplet thenTriplet =
                        TripletFactory.createTriplet(
                                view.getThenTripletSubject(), thenRelation, thenObject);

                RelationMapping mapping = new RelationMapping(thenTriplet, getAndTripletsList());

                relation.get().setMapping(mapping);

                if (!doesRelationExist(relation.get())) {
                    RulesConceptsAndRelations.getInstance()
                            .getRelationManager()
                            .addRelation(relation.get());
                }

                inputRemote.switchToArchitectureRulesView();
            } catch (UnrelatedMappingException
                    | UnsupportedObjectTypeInTriplet
                    | InvalidVariableNameException
                    | RelationAlreadyExistsException e) {
                // not possible/fatal
                throw new RuntimeException(e.getMessage());
            } catch (SubjectOrObjectNotDefinedException e) {
                view.showThenSubjectOrObjectErrorMessage("Setting this is required");
            }
        } else {
            view.showNameFieldErrorMessage("A name is required");
        }
    }

    private boolean doesRelationExist(CustomRelation relation) {
        return RulesConceptsAndRelations.getInstance()
                .getRelationManager()
                .doesRelationExist(relation);
    }

    @Override
    public void descriptionHasChanged(MappingDescriptionFieldChangedEvent event) {
        relation.get().setDescription(event.getNewDescription());
    }
}
