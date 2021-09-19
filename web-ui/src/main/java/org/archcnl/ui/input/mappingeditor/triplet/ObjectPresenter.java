package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.Optional;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;
import org.archcnl.domain.input.model.mappings.BooleanValue;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.Relation;
import org.archcnl.domain.input.model.mappings.StringValue;
import org.archcnl.domain.input.model.mappings.TypeRelation;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.View;

public class ObjectPresenter implements ObjectContract.Presenter<View> {

    private static final long serialVersionUID = -6274011448119690642L;
    private View view;

    @Override
    public void setView(View view) {
        this.view = view;
    }

    public ObjectType getObject()
            throws ConceptDoesNotExistException, ObjectNotDefinedException,
                    VariableDoesNotExistException, SubjectNotDefinedException {
        return view.getObject();
    }

    public void predicateHasChanged(Optional<Relation> relationOptional) {
        if (relationOptional.isEmpty()) {
            view.clearView();
        } else {
            Relation relation = relationOptional.orElseThrow();
            if (relation instanceof TypeRelation) {
                view.switchToConceptView();
            } else if (relation.canRelateToObjectType(new StringValue(""))) {
                // only works correctly under the assumption that a relation that can relate to a
                // string cannot relate to any other ObjectType
                view.switchToStringView();
            } else if (relation.canRelateToObjectType(new BooleanValue(false))) {
                // only works correctly under the assumption that a relation that can relate to a
                // boolean cannot relate to any other ObjectType
                view.switchToBooleanView();
            } else {
                // only works correctly under the assumption that a relation that can relate to a
                // concept cannot relate to any other ObjectType
                view.switchToVariableView();
            }
        }
    }
}
