package org.archcnl.ui.input.mappingeditor.triplet;

import java.io.Serializable;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectNotDefinedException;

public interface ObjectContract {

    public interface View {

        public ObjectType getObject()
                throws ConceptDoesNotExistException, ObjectNotDefinedException,
                        VariableDoesNotExistException, SubjectNotDefinedException;

        public void clearView();

        public void switchToConceptView();

        public void switchToVariableView();

        public void switchToBooleanView();

        public void switchToStringView();
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);
    }
}
