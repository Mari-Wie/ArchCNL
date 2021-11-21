package org.archcnl.ui.input.mappingeditor.triplet;

import java.io.Serializable;
import org.archcnl.domain.common.Variable;

public interface VariableListContract {

    public interface View {

        public void addVariableView(Variable variable);
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);
    }
}
