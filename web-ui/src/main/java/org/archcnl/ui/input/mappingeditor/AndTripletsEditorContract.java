package org.archcnl.ui.input.mappingeditor;

import java.io.Serializable;
import java.util.List;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract;
import org.archcnl.ui.input.mappingeditor.triplet.TripletPresenter;

public interface AndTripletsEditorContract {

    public interface View {

        public void addNewTripletViewAfter(TripletContract.View tripletView);

        public void deleteTripletView(TripletContract.View tripletView);

        public List<TripletPresenter> getTripletPresenters();

        public void setAddButtonVisible(boolean visible);
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);

        public VariableManager getVariableManager();

        public void addNewTripletViewAfter(TripletContract.View tripletView);

        public void deleteTripletView(TripletContract.View view);

        public void mouseEnter();

        public void mouseLeave();

        public void addButtonPressed();

        public boolean isLastAndTripletsEditor();

        public void delete();
    }
}
