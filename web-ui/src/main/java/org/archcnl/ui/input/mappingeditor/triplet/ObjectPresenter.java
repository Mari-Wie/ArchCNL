package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.Optional;
import org.archcnl.domain.common.BooleanValue;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.StringValue;
import org.archcnl.domain.common.TypeRelation;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.input.mappingeditor.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.PredicateCannotRelateToObjectException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.View;

public class ObjectPresenter implements ObjectContract.Presenter<View> {

    private static final long serialVersionUID = -6274011448119690642L;
    private View view;
    private VariableManager variableManager;

    public ObjectPresenter(VariableManager variableManager) {
        this.variableManager = variableManager;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    public ObjectType getObject()
            throws ConceptDoesNotExistException, ObjectNotDefinedException,
                    InvalidVariableNameException, SubjectOrObjectNotDefinedException {
        return view.getObject();
    }

    public void setObject(ObjectType object) throws PredicateCannotRelateToObjectException {
        view.setObject(object);
    }

    public void predicateHasChanged(Optional<Relation> relationOptional) {
        if (relationOptional.isEmpty()) {
            view.clearView();
        } else {
            Relation relation = relationOptional.orElseThrow();
            if (relation instanceof TypeRelation) {
                view.switchToConceptView();
            } else {
                boolean stringsAllowed = relation.canRelateToObjectType(new StringValue(""));
                boolean booleansAllowed = relation.canRelateToObjectType(new BooleanValue(false));
                view.switchToVariableStringBooleanView(
                        variableManager, stringsAllowed, booleansAllowed);
            }
        }
    }

    public void highlightWhenEmpty() {
        try {
            getObject();
        } catch (ConceptDoesNotExistException e) {
            view.showErrorMessage("Concept does not exist");
        } catch (ObjectNotDefinedException | SubjectOrObjectNotDefinedException e) {
            view.showErrorMessage("Object not set");
        } catch (InvalidVariableNameException e) {
            view.showErrorMessage("Invalid Variable name");
        }
    }
}
