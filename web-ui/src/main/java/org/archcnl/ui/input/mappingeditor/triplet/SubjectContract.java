package org.archcnl.ui.input.mappingeditor.triplet;

import java.io.Serializable;
import java.util.List;
import org.archcnl.domain.input.model.mappings.Variable;

public interface SubjectContract {

    public interface View {}

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);

        public List<Variable> getVariables();
    }
}
