package org.archcnl.ui.input.mappingeditor;

import java.io.Serializable;
import java.util.List;
import org.archcnl.domain.input.model.mappings.VariableManager;

public interface MappingEditorContract {

    public interface View {

        public void addNewAndTripletsViewAfter(AndTripletsEditorContract.View andTripletsView);

        public void deleteAndTripletsView(AndTripletsEditorContract.View andTripletsView);

        public List<AndTripletsEditorPresenter> getAndTripletsPresenters();
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);

        public void nameHasChanged(String newName);

        public VariableManager getVariableManager();

        public void addNewAndTripletsViewAfter(AndTripletsEditorContract.View andTripletsView);

        public void deleteAndTripletsView(AndTripletsEditorContract.View andTripletsView);

        public int numberOfAndTriplets();
    }
}
