package org.archcnl.ui.input.mappingeditor;

import java.io.Serializable;

import org.archcnl.domain.input.model.mappings.VariableManager;

public interface MappingCreationContract {

    public interface View<T extends Presenter> {

        public void setPresenter(T presenter);
    }

    public interface Presenter extends Serializable {

        public void nameHasChanged(String newName);
        
        public VariableManager getVariableManager();
        
        //public void saveMapping();
        
    }
}
