package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.BooleanValue;
import org.archcnl.domain.common.Concept;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.StringValue;
import org.archcnl.domain.common.TypeRelation;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;

public class ObjectView extends HorizontalLayout {

    private static final long serialVersionUID = -1105253743414019620L;
    private static final String CONCEPT = "Concept";
    private static final String OBJECT = "Object";
    private static final String VAR_STRING_BOOL = "VariableStringBool";
    private ConceptSelectionComponent conceptSelectionComponent;
    private VariableStringBoolSelectionView variableStringBoolSelectionView;
    private String currentSelectionComponentString = "";

    public ObjectView() {
        conceptSelectionComponent = new ConceptSelectionComponent();
        variableStringBoolSelectionView = new VariableStringBoolSelectionView();

        conceptSelectionComponent.addListener(
                ConceptListUpdateRequestedEvent.class, this::fireEvent);
        conceptSelectionComponent.addListener(ConceptSelectedEvent.class, this::fireEvent);

        variableStringBoolSelectionView.addListener(
                VariableFilterChangedEvent.class, this::fireEvent);
        variableStringBoolSelectionView.addListener(
                VariableCreationRequestedEvent.class, this::fireEvent);
        variableStringBoolSelectionView.addListener(
                VariableListUpdateRequestedEvent.class, this::fireEvent);
    }

    private void switchToConceptView() {
        currentSelectionComponentString = ObjectView.CONCEPT;
        add(conceptSelectionComponent);
    }

    private void switchToVariableStringBooleanView(
            boolean stringsAllowed, boolean booleansAllowed) {
        currentSelectionComponentString = ObjectView.VAR_STRING_BOOL;
        variableStringBoolSelectionView.updateTypeSelectionItems(stringsAllowed, booleansAllowed);
        add(variableStringBoolSelectionView);
    }

    public ObjectType getObject()
            throws ConceptDoesNotExistException, ObjectNotDefinedException,
                    InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        ObjectType object;
        if (currentSelectionComponentString.equals(ObjectView.CONCEPT)
                && conceptSelectionComponent.getSelectedItem().isPresent()) {
            String conceptName = conceptSelectionComponent.getSelectedItem().get();
            // TODO: The RulesConceptsAndRelations call does not belong here
            object =
                    RulesConceptsAndRelations.getInstance()
                            .getConceptManager()
                            .getConceptByName(conceptName)
                            .orElseThrow(() -> new ConceptDoesNotExistException(conceptName));
        } else if (currentSelectionComponentString.equals(ObjectView.VAR_STRING_BOOL)) {
            object = variableStringBoolSelectionView.getObject();
        } else {
            throw new ObjectNotDefinedException();
        }
        return object;
    }

    public void setObject(ObjectType object) {
        if (object instanceof Concept) {
            conceptSelectionComponent.setObject((Concept) object);
        } else {
            variableStringBoolSelectionView.setObject(object);
        }
    }

    public void predicateHasChanged(Optional<Relation> relationOptional) {
        removeAll();
        // Null check is a temporary fix to prevent a NullPointerException
        // that occured when a ValueChangedEvent with null is thrown during QueryEditor switching
        if (relationOptional != null && relationOptional.isPresent()) {
            Relation relation = relationOptional.get();
            if (relation instanceof TypeRelation) {
                switchToConceptView();
            } else {
                boolean stringsAllowed = relation.canRelateToObjectType(new StringValue(""));
                boolean booleansAllowed = relation.canRelateToObjectType(new BooleanValue(false));
                switchToVariableStringBooleanView(stringsAllowed, booleansAllowed);
            }
        }
    }

    public void highlightWhenEmpty() {
        try {
            getObject();
        } catch (ConceptDoesNotExistException e) {
            showErrorMessage("Concept does not exist");
        } catch (ObjectNotDefinedException | SubjectOrObjectNotDefinedException e) {
            showErrorMessage("Object not set");
        } catch (InvalidVariableNameException e) {
            showErrorMessage("Invalid Variable name");
        }
    }

    private void showErrorMessage(String errorMessage) {
        if (currentSelectionComponentString.equals(ObjectView.CONCEPT)) {
            conceptSelectionComponent.showErrorMessage(errorMessage);
        } else if (currentSelectionComponentString.equals(ObjectView.VAR_STRING_BOOL)) {
            variableStringBoolSelectionView.showErrorMessage(errorMessage);
        }
        // there is no need to show the errorMessage when both views are null
        // as in that case the predicate is not set and the actual error message
        // is shown there
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void setLabel() {
        conceptSelectionComponent.setLabel(OBJECT);
        variableStringBoolSelectionView.setLabel(OBJECT);
    }
}
