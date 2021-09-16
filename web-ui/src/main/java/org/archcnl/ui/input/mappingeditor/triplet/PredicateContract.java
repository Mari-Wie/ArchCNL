package org.archcnl.ui.input.mappingeditor.triplet;

import java.io.Serializable;

public interface PredicateContract {

    public interface View {}

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);
    }
}
