package org.archcnl.ui.input.mappingeditor.triplet;

import java.io.Serializable;
import java.util.List;

import org.archcnl.domain.input.model.mappings.Variable;

public interface SubjectContract {
	
	public interface View<T extends Presenter> {

        public void setPresenter(T presenter);
    }

    public interface Presenter extends Serializable {
    	
    	public List<Variable> getVariables();
        
    }
}
