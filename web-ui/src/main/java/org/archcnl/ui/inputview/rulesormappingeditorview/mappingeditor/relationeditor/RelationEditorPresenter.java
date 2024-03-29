package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorPresenter;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RulesWidgetRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events.MappingDescriptionFieldChangedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events.AddCustomRelationRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events.ChangeRelationNameRequestedEvent;

public class RelationEditorPresenter extends MappingEditorPresenter {

    private static final long serialVersionUID = -8403313455385623145L;
    private RelationEditorView view;
    private Optional<CustomRelation> relation = Optional.empty();

    public RelationEditorPresenter() {
        super();
        view =
                new RelationEditorView(
                        prepareAndTripletsEditorView(new AndTripletsEditorPresenter(true)));
        initializeView(view);
        addRelationViewListeners();
    }

    public void showRelation(final CustomRelation relation) {
        this.relation = Optional.of(relation);
        showAndTriplets(extractAndTriplets(relation));
        updateInfoFieldsAndThenTriplet();
    }

    private void addRelationViewListeners() {
        view.addListener(VariableCreationRequestedEvent.class, this::addVariable);
        view.addListener(
                VariableListUpdateRequestedEvent.class,
                event -> event.handleEvent(variableManager));
    }

    private List<AndTriplets> extractAndTriplets(final CustomRelation relation) {
        if (relation == null || relation.getMapping().isEmpty()) {
            return new LinkedList<>();
        } else {
            return relation.getMapping().get().getWhenTriplets();
        }
    }

    // Stub
    @Override
    protected void updateMappingName(final String newName) {
        if (relation.isEmpty()) {
            relation =
                    Optional.of(
                            new CustomRelation(
                                    newName, "", new LinkedHashSet<>(), new LinkedHashSet<>()));
        } else {
            fireEvent(new ChangeRelationNameRequestedEvent(this, true, relation.get(), newName));
        }
    }

    @Override
    protected void updateInfoFieldsAndThenTriplet() {
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
    protected void updateMapping() {
        if (relation.isPresent()) {
            try {
                relation.get().setDescription(view.getDescription());
                final Triplet thenTriplet =
                        new Triplet(
                                view.getThenTripletSubject(),
                                relation.get(),
                                view.getThenTripletObject());

                final RelationMapping mapping =
                        new RelationMapping(thenTriplet, getAndTripletsList());
                fireEvent(new AddCustomRelationRequestedEvent(this, true, relation.get(), mapping));
                fireEvent(new RulesWidgetRequestedEvent(this, true));
            } catch (final SubjectOrObjectNotDefinedException e) {
                view.showThenSubjectOrObjectErrorMessage("Setting this is required");
            }
        } else {
            view.showNameFieldErrorMessage("A name is required");
        }
    }

    @Override
    public void descriptionHasChanged(final MappingDescriptionFieldChangedEvent event) {
        if (relation.isPresent()) {
            relation.get().setDescription(event.getNewDescription());
        }
    }
}
