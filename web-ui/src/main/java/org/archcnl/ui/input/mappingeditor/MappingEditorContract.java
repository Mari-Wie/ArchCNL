package org.archcnl.ui.input.mappingeditor;

import java.io.Serializable;
import org.archcnl.domain.input.model.mappings.VariableManager;

public interface MappingEditorContract {

    public interface View {}

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);

        public void nameHasChanged(String newName);

        public VariableManager getVariableManager();
    }
}
