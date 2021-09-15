package org.archcnl.ui.input.mappingeditor;

import java.io.Serializable;

public interface MappingCreationContract {

    public interface View<T extends Presenter> {

        public void setPresenter(T presenter);
    }

    public interface Presenter extends Serializable {

        public void nameHasChanged(String newName);
    }
}
