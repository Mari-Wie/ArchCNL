package org.archcnl.ui.input.mappingeditor.triplet;

import java.io.Serializable;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.ui.input.mappingeditor.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.PredicateCannotRelateToObjectException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public interface ObjectContract {

    public interface View {

        public ObjectType getObject()
                throws ConceptDoesNotExistException, ObjectNotDefinedException,
                        SubjectOrObjectNotDefinedException, InvalidVariableNameException;

        public void setObject(ObjectType object) throws PredicateCannotRelateToObjectException;

        public void clearView();

        public void switchToConceptView();

        public void switchToVariableStringBooleanView(
                VariableManager variableManager, boolean stringsAllowed, boolean booleansAllowed);
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);
    }
}
